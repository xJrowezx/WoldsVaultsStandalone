package xyz.iwolfking.woldsvaults.objectives;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import iskallia.vault.block.MonolithBlock;
import iskallia.vault.block.PlaceholderBlock;
import iskallia.vault.block.entity.MonolithTileEntity;
import iskallia.vault.client.gui.helper.LightmapHelper;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.LootTableKey;
import iskallia.vault.core.data.key.SupplierKey;
import iskallia.vault.core.event.CommonEvents;
import iskallia.vault.core.event.common.BlockSetEvent;
import iskallia.vault.core.event.common.BlockUseEvent;
import iskallia.vault.core.random.ChunkRandom;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.VaultLevel;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.core.vault.objective.KillBossObjective;
import iskallia.vault.core.vault.objective.MonolithObjective;
import iskallia.vault.core.vault.objective.Objective;
import iskallia.vault.core.vault.player.Listener;
import iskallia.vault.core.world.data.entity.PartialCompoundNbt;
import iskallia.vault.core.world.data.tile.PartialBlockState;
import iskallia.vault.core.world.data.tile.PartialTile;
import iskallia.vault.core.world.loot.generator.LootTableGenerator;
import iskallia.vault.core.world.storage.VirtualWorld;
import iskallia.vault.init.*;
import iskallia.vault.item.gear.DataInitializationItem;
import iskallia.vault.item.gear.DataTransferItem;
import iskallia.vault.item.gear.VaultLevelItem;
import iskallia.vault.network.message.MonolithIgniteMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import vazkii.quark.content.mobs.entity.Wraith;
import xyz.iwolfking.woldsvaults.WoldsVaults;
import xyz.iwolfking.woldsvaults.util.VaultModifierUtils;

import java.util.*;

public class HauntedBraziersObjective extends MonolithObjective {
    public static final SupplierKey<Objective> E_KEY = SupplierKey.of("haunted_braziers", Objective.class).with(Version.v1_12, HauntedBraziersObjective::new);
    public static final ResourceLocation HAUNTED_HUD = WoldsVaults.id("textures/gui/monolith/haunted_hud.png");
    public HauntedBraziersObjective() {}

    public boolean isOverStacking() {
        return this.get(COUNT) >= this.get(TARGET);
    }

    @Override
    public SupplierKey<Objective> getKey() {
        return E_KEY;
    }

    public HauntedBraziersObjective(int target, float objectiveProbability, ResourceLocation stackModifierPool, ResourceLocation overStackModifierPool, ResourceLocation lootTable) {
        this.set(COUNT, 0);
        this.set(TARGET, target);
        this.set(BASE_TARGET, target);
        this.set(OBJECTIVE_PROBABILITY, objectiveProbability);
        this.set(STACK_MODIFIER_POOL, stackModifierPool);
        this.set(OVER_STACK_MODIFIER_POOL, overStackModifierPool);
        this.set(OVER_STACK_LOOT_TABLE, lootTable);
    }

    public static HauntedBraziersObjective of(int target, float objectiveProbability, ResourceLocation stackModifierPool, ResourceLocation overStackModifierPool, ResourceLocation overStackLootTable) {
        return new HauntedBraziersObjective(target, objectiveProbability, stackModifierPool, overStackModifierPool, overStackLootTable);
    }


    @Override
    public void initServer(VirtualWorld world, Vault vault) {
        boolean hasGeneratedModifiers = false;
        for(VaultModifier<?> modifier : vault.get(Vault.MODIFIERS).getModifiers()) {
            if(modifier.getId().equals(WoldsVaults.id("ghost_town"))) {
                hasGeneratedModifiers = true;
            }
        }

        if(!hasGeneratedModifiers) {
            VaultModifierUtils.addModifier(vault, WoldsVaults.id("ghost_town"), 1);
            VaultModifierUtils.addModifier(vault, WoldsVaults.id("haunting"), 1);
        }

        CommonEvents.OBJECTIVE_PIECE_GENERATION.register(this, (data) -> {
            if (data.getVault() == vault) {
                this.ifPresent(OBJECTIVE_PROBABILITY, probability -> data.setProbability(probability));
            }
        });
        this.registerObjectiveTemplate(world, vault);

        CommonEvents.BLOCK_USE.in(world).at(BlockUseEvent.Phase.HEAD).of(ModBlocks.MONOLITH).register(this, (data) -> {
            if (data.getHand() != InteractionHand.MAIN_HAND) {
                data.setResult(InteractionResult.SUCCESS);
                return;
            } else if (data.getState().getValue(MonolithBlock.STATE) != MonolithBlock.State.EXTINGUISHED || vault.get(Vault.LISTENERS).getObjectivePriority(data.getPlayer().getUUID(), this) != 0) {
                return;
            }

            BlockPos pos = data.getPos();
            boolean overStacking = isOverStacking();
            world.setBlock(pos, world.getBlockState(pos).setValue(MonolithBlock.STATE, overStacking ? MonolithBlock.State.DESTROYED : MonolithBlock.State.LIT), 3);

            this.playActivationEffects(world, pos, overStacking);
            this.set(COUNT, this.get(COUNT) + 1);
            if (!overStacking) {
                for (Objective objective : this.get(CHILDREN)) {
                    if (objective instanceof KillBossObjective killBoss) {
                        killBoss.set(KillBossObjective.BOSS_POS, pos);
                    }
                }
                List<Wraith> nearbyEntities = world.getEntitiesOfClass(Wraith.class, Objects.requireNonNull(world.getBlockEntity(pos)).getRenderBoundingBox().inflate(64.0D));
                for (Wraith livingEntity : nearbyEntities) {
                    livingEntity.addEffect(new MobEffectInstance(ModEffects.NO_AI, 60, 0));
                    livingEntity.hurt(DamageSource.MAGIC, livingEntity.getMaxHealth() / 2);
                }
            }

            if (overStacking) {
                LootTableKey table = VaultRegistry.LOOT_TABLE.getKey(this.get(OVER_STACK_LOOT_TABLE));
                if (table == null) {
                    return;
                }

                LootTableGenerator generator = new LootTableGenerator(vault.get(Vault.VERSION), table, 0.0F);
                ChunkRandom random = ChunkRandom.any();
                random.setBlockSeed(vault.get(Vault.SEED), data.getPos(), 900397371L);
                generator.generate(random);
                List<ItemStack> loot = new ArrayList<>();
                Iterator<ItemStack> generated = generator.getItems();
                generated.forEachRemaining(loot::add);

                for(int i = 0; i < loot.size(); ++i) {
                    ItemStack stack = loot.get(i);
                    VaultLevelItem.doInitializeVaultLoot(stack, vault, null);
                    stack = DataTransferItem.doConvertStack(stack);
                    DataInitializationItem.doInitialize(stack);
                    loot.set(i, stack);
                }

                loot.removeIf(ItemStack::isEmpty);
                loot.forEach((stack) -> Block.popResource(world, pos, stack));
                data.setResult(InteractionResult.SUCCESS);
            }

            if (data.getWorld().getBlockEntity(pos) instanceof MonolithTileEntity tile && !tile.getModifiers().isEmpty()) {
                ChunkRandom random = ChunkRandom.any();
                random.setBlockSeed(vault.get(Vault.SEED), data.getPos(), 900397371L);

                var display = data.getPlayer().getDisplayName().plainCopy().append(" added ").withStyle(ChatFormatting.GRAY);
                for (Map.Entry<ResourceLocation, Integer> entry : tile.getModifiers().entrySet()) {
                    if (display.getSiblings().size() > 1) {
                        display.append(", ");
                    }
                    VaultModifier<?> modifier = VaultModifierRegistry.get(entry.getKey());
                    display.append(modifier.getChatDisplayNameComponent(entry.getValue()));
                    vault.get(Vault.MODIFIERS).addModifier(modifier, entry.getValue(), true, random);
                }
                display.append(".").withStyle(ChatFormatting.GRAY);

                for (Listener listener : vault.get(Vault.LISTENERS).getAll()) {
                    listener.getPlayer().ifPresent((other) -> {
                        world.playSound(null, other.getX(), other.getY(), other.getZ(), SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 0.9F, 1.2F);
                        other.displayClientMessage(display, false);
                    });
                }
            }

            data.setResult(InteractionResult.SUCCESS);
        });
        CommonEvents.BLOCK_SET.at(BlockSetEvent.Type.RETURN).in(world).register(this, (data) -> {
            PartialTile target = PartialTile.of(PartialBlockState.of(ModBlocks.PLACEHOLDER), PartialCompoundNbt.empty());
            target.getState().set(PlaceholderBlock.TYPE, PlaceholderBlock.Type.OBJECTIVE);
            if (target.isSubsetOf(PartialTile.of(data.getState()))) {
                data.getWorld().setBlock(data.getPos(), ModBlocks.MONOLITH.defaultBlockState(), 3);
            }
        });
        CommonEvents.MONOLITH_UPDATE.register(this, (data) -> {
            if (data.getWorld() != world || data.getEntity().isGenerated() || data.getEntity().isOverStacking() == isOverStacking() || data.getState().getValue(MonolithBlock.STATE) != MonolithBlock.State.EXTINGUISHED) {
                return;
            }

            boolean overStacking = isOverStacking();
            data.getEntity().setOverStacking(overStacking);
            if (overStacking) {
                data.getEntity().removeModifiers();
            }

            ResourceLocation pool = overStacking ? this.get(OVER_STACK_MODIFIER_POOL) : this.get(STACK_MODIFIER_POOL);
            if (pool != null) {
                int level = vault.get(Vault.LEVEL).getOr(VaultLevel.VALUE, 0);
                ChunkRandom random = ChunkRandom.any();
                random.setBlockSeed(vault.get(Vault.SEED), data.getPos(), 90039737L);
                for (VaultModifier<?> vaultModifier : ModConfigs.VAULT_MODIFIER_POOLS.getRandom(pool, level, random)) {
                    data.getEntity().addModifier(vaultModifier);
                }
            }

            data.getEntity().setGenerated(true);
        });
        this.get(CHILDREN).forEach((child) -> child.initServer(world, vault));
    }

    @Override
    public boolean isActive(VirtualWorld world, Vault vault, Objective objective) {
        if (this.get(COUNT) < this.get(TARGET)) {
            return objective == this;
        }

        for (Objective child : this.get(CHILDREN)) {
            if (child.isActive(world, vault, objective)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void tickServer(VirtualWorld world, Vault vault) {
        double increase = CommonEvents.OBJECTIVE_TARGET.invoke(world, vault, 0.0).getIncrease();
        this.set(TARGET, (int) Math.round((double) this.get(BASE_TARGET) * (1.0 + increase)));
        if (isOverStacking()) {
            this.get(CHILDREN).forEach((child) -> child.tickServer(world, vault));
        }
    }

    @Override
    protected void playActivationEffects(VirtualWorld world, BlockPos pos, boolean overStacking) {
        if (overStacking) {
            BlockParticleOption particle = new BlockParticleOption(ParticleTypes.BLOCK, world.getBlockState(pos));
            Vec3 vec3 = new Vec3(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
            world.sendParticles(particle, vec3.x, vec3.y, vec3.z, 400, 1.0, 1.0, 1.0, 0.5);
            world.sendParticles(ParticleTypes.SCRAPE, vec3.x, vec3.y, vec3.z, 50, 1.0, 1.0, 1.0, 0.5);
            world.playSound(null, vec3.x, vec3.y, vec3.z, ModSounds.DESTROY_MONOLITH, SoundSource.PLAYERS, 0.25F, 1.0F);
        } else {
            ModNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new MonolithIgniteMessage(pos));
            world.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean render(Vault vault, PoseStack matrixStack, Window window, float partialTicks, Player player) {
        MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Font font = Minecraft.getInstance().font;
        int midX = window.getGuiScaledWidth() / 2;
        if (isOverStacking()) {
            Component status = new TextComponent("Grave Rob, or Exit to Complete").withStyle(ChatFormatting.AQUA);
            float width = (float) midX - (float) font.width(status) / 2.0F;
            font.drawInBatch(status, width, 9.0F, -1, true, matrixStack.last().pose(), buffer, false, 0, LightmapHelper.getPackedFullbrightCoords());
            buffer.endBatch();
            return true;
        }

        int current = this.get(COUNT);
        int total = this.get(TARGET);
        Component status = new TextComponent(current + " / " + total).withStyle(ChatFormatting.WHITE);

        matrixStack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int previousTexture = RenderSystem.getShaderTexture(0);
        RenderSystem.setShaderTexture(0, HAUNTED_HUD);
        float progress = (float) this.get(COUNT) / (float) this.get(TARGET);
        matrixStack.translate(midX - 80, 8.0, 0.0);
        GuiComponent.blit(matrixStack, 0, 0, 0.0F, 0.0F, 200, 26, 200, 100);
        GuiComponent.blit(matrixStack, 0, 8, 0.0F, 30.0F, 13 + (int) (130.0F * progress), 10, 200, 100);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, previousTexture);
        matrixStack.popPose();

        matrixStack.pushPose();
        matrixStack.scale(0.6F, 0.6F, 0.6F);
        float width = (float ) midX / 0.6F - (float) font.width(status) / 2.0F;
        font.drawInBatch(status, width, (float)(9 + 22), -1, true, matrixStack.last().pose(), buffer, false, 0, LightmapHelper.getPackedFullbrightCoords());
        buffer.endBatch();
        matrixStack.popPose();
        return true;
    }
}
