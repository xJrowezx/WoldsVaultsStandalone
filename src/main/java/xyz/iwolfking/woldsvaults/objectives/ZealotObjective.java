package xyz.iwolfking.woldsvaults.objectives;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import iskallia.vault.client.gui.helper.LightmapHelper;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.data.key.FieldKey;
import iskallia.vault.core.data.key.SupplierKey;
import iskallia.vault.core.data.key.registry.FieldRegistry;
import iskallia.vault.core.event.CommonEvents;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.objective.Objective;
import iskallia.vault.core.vault.player.Listener;
import iskallia.vault.core.vault.player.Runner;
import iskallia.vault.core.world.storage.VirtualWorld;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.iwolfking.vhapi.api.events.vault.VaultEvents;
import xyz.iwolfking.woldsvaults.WoldsVaults;


import java.util.Iterator;
import java.util.Objects;

public class ZealotObjective extends Objective {
    public static final ResourceLocation HUD = WoldsVaults.id("textures/gui/zealot/hud.png");

    public static final SupplierKey<Objective> KEY = SupplierKey.of("zealot", Objective.class).with(Version.latest(), ZealotObjective::new);
    public static final FieldRegistry FIELDS = (FieldRegistry)Objective.FIELDS.merge(new FieldRegistry());
    public static final FieldKey<Integer> COUNT = (FieldKey)FieldKey.of("count", Integer.class).with(Version.v1_0, Adapters.INT_SEGMENTED_3, DISK.all().or(CLIENT.all())).register(FIELDS);
    public static final FieldKey<Integer> TARGET = (FieldKey)FieldKey.of("target", Integer.class).with(Version.v1_0, Adapters.INT_SEGMENTED_3, DISK.all().or(CLIENT.all())).register(FIELDS);
    public static final FieldKey<Integer> BASE_TARGET = (FieldKey)FieldKey.of("base_target", Integer.class).with(Version.v1_25, Adapters.INT_SEGMENTED_3, DISK.all().or(CLIENT.all())).register(FIELDS);
    public static final FieldKey<Float> OBJECTIVE_PROBABILITY = (FieldKey)FieldKey.of("objective_probability", Float.class).with(Version.v1_2, Adapters.FLOAT, DISK.all()).register(FIELDS);


    protected ZealotObjective() {
    }

    protected ZealotObjective(int target, float objectiveProbability) {
        this.set(COUNT, 0);
        this.set(TARGET, target);
        this.set(BASE_TARGET, target);
        this.set(OBJECTIVE_PROBABILITY, objectiveProbability);
    }

    public static ZealotObjective of(int target, float objectiveProbability) {
        return new ZealotObjective(target, objectiveProbability);
    }


    public FieldRegistry getFields() {
        return FIELDS;
    }

    public void initServer(VirtualWorld world, Vault vault) {
        CommonEvents.OBJECTIVE_PIECE_GENERATION.register(this, (data) -> {
            this.ifPresent(OBJECTIVE_PROBABILITY, (probability) -> {
                data.setProbability(0F);
            });
        });
        VaultEvents.GOD_ALTAR_COMPLETED.in(world).register(this, (data -> {
            this.set(COUNT, (Integer)this.get(COUNT) + 1);
        }));
        super.initServer(world, vault);
    }

    public void tickServer(VirtualWorld world, Vault vault) {
        this.ifPresent(BASE_TARGET, (value) -> {
            double increase = CommonEvents.OBJECTIVE_TARGET.invoke(world, vault, 0.0).getIncrease();
            this.set(TARGET, (int)Math.round((double)(Integer)this.get(BASE_TARGET) * (1.0 + increase)));
        });
        if ((Integer)this.get(COUNT) >= (Integer)this.get(TARGET)) {
            super.tickServer(world, vault);
        }

    }

    public void tickListener(VirtualWorld world, Vault vault, Listener listener) {
        if (listener.getPriority(this) < 0) {
            listener.addObjective(vault, this);
        }

        if (listener instanceof Runner && (Integer)this.get(COUNT) >= (Integer)this.get(TARGET)) {
            super.tickListener(world, vault, listener);
        }

    }


    @OnlyIn(Dist.CLIENT)
    public boolean render(Vault vault, PoseStack matrixStack, Window window, float partialTicks, Player player) {
        int current;
        FormattedCharSequence var10001;
        float var10002;
        if ((Integer)this.get(COUNT) >= (Integer)this.get(TARGET)) {
            current = window.getGuiScaledWidth() / 2;
            Font font = Minecraft.getInstance().font;
            MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            Component txt = (new TextComponent("The gods are pleased, you may exit when ready.")).withStyle(ChatFormatting.GOLD);
            var10001 = txt.getVisualOrderText();
            var10002 = (float)current - (float)font.width(txt) / 2.0F;
            Objects.requireNonNull(font);
            font.drawInBatch(var10001, var10002, 9.0F, -1, true, matrixStack.last().pose(), buffer, false, 0, LightmapHelper.getPackedFullbrightCoords());
            buffer.endBatch();
            return true;
        } else {
            current = (Integer)this.get(COUNT);
            int total = (Integer)this.get(TARGET);
            Component txt = (new TextComponent(String.valueOf(current))).withStyle(ChatFormatting.WHITE).append((new TextComponent(" / ")).withStyle(ChatFormatting.WHITE)).append((new TextComponent(String.valueOf(total))).withStyle(ChatFormatting.WHITE));
            int midX = window.getGuiScaledWidth() / 2;
            matrixStack.pushPose();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int previousTexture = RenderSystem.getShaderTexture(0);
            RenderSystem.setShaderTexture(0, HUD);
            float progress = (float)current / (float)total;
            matrixStack.translate((double)(midX - 80), 8.0, 0.0);
            GuiComponent.blit(matrixStack, 0, 0, 0.0F, 0.0F, 200, 26, 200, 100);
            GuiComponent.blit(matrixStack, 0, 8, 0.0F, 30.0F, 13 + (int)(130.0F * progress), 10, 200, 100);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, previousTexture);
            matrixStack.popPose();
            Font font = Minecraft.getInstance().font;
            MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            matrixStack.pushPose();
            matrixStack.scale(0.6F, 0.6F, 0.6F);
            var10001 = txt.getVisualOrderText();
            var10002 = (float)midX / 0.6F - (float)font.width(txt) / 2.0F;
            Objects.requireNonNull(font);
            font.drawInBatch(var10001, var10002, (float)(9 + 22), -1, true, matrixStack.last().pose(), buffer, false, 0, LightmapHelper.getPackedFullbrightCoords());
            buffer.endBatch();
            matrixStack.popPose();
            return true;
        }
    }

    @Override
    public boolean isActive(VirtualWorld virtualWorld, Vault vault, Objective objective) {
        if ((Integer)this.get(COUNT) < (Integer)this.get(TARGET)) {
            return objective == this;
        } else {
            Iterator var4 = ((ObjList)this.get(CHILDREN)).iterator();

            Objective child;
            do {
                if (!var4.hasNext()) {
                    return false;
                }

                child = (Objective)var4.next();
            } while(!child.isActive(virtualWorld, vault, objective));

            return true;
        }
    }

    @Override
    public SupplierKey<Objective> getKey() {
        return KEY;
    }


}
