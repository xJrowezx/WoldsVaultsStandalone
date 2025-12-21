package xyz.iwolfking.woldsvaults.objectives;

import iskallia.vault.VaultMod;
import iskallia.vault.block.DivineAltarBlock;
import iskallia.vault.block.PlaceholderBlock;
import iskallia.vault.block.entity.ScavengerAltarTileEntity;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.data.adapter.basic.EnumAdapter;
import iskallia.vault.core.data.key.FieldKey;
import iskallia.vault.core.data.key.SupplierKey;
import iskallia.vault.core.event.CommonEvents;
import iskallia.vault.core.event.common.BlockSetEvent;
import iskallia.vault.core.random.JavaRandom;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.VaultLevel;
import iskallia.vault.core.vault.objective.Objective;
import iskallia.vault.core.vault.objective.ScavengerObjective;
import iskallia.vault.core.vault.objective.scavenger.ScavengeTask;
import iskallia.vault.core.vault.objective.scavenger.ScavengerGoal;
import iskallia.vault.core.vault.player.Listener;
import iskallia.vault.core.vault.player.Listeners;
import iskallia.vault.core.vault.player.Runner;
import iskallia.vault.core.world.data.entity.PartialCompoundNbt;
import iskallia.vault.core.world.data.tile.PartialBlockState;
import iskallia.vault.core.world.data.tile.PartialTile;
import iskallia.vault.core.world.storage.VirtualWorld;
import iskallia.vault.init.ModBlocks;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.GodBlessingItem;
import iskallia.vault.item.KeystoneItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.loading.LoadingModList;
import xyz.iwolfking.woldsvaults.configs.UnhingedScavengerConfig;


import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class UnhingedScavengerObjective extends ScavengerObjective {

    public static final FieldKey<Config> CONFIG = (FieldKey)FieldKey.of("config", Config.class).with(Version.v1_19, Adapters.ofEnum(Config.class, EnumAdapter.Mode.ORDINAL), DISK.all()).register(FIELDS);;

    public static final SupplierKey<Objective> E_KEY = (SupplierKey)SupplierKey.of("unhinged_scavenger", Objective.class).with(Version.v1_12, UnhingedScavengerObjective::new);

    public static final FieldKey<ResourceLocation> ENTRY_POOL;

    @Override
    public SupplierKey<Objective> getKey() {
        return E_KEY;
    }
    public UnhingedScavengerObjective(float objectiveProbability, Config config, ResourceLocation pool) {
        this.set(GOALS, new GoalMap());
        this.set(OBJECTIVE_PROBABILITY, objectiveProbability);
        this.set(CONFIG, config);
        this.set(ENTRY_POOL, pool);
    }

    public UnhingedScavengerObjective() {
        this.set(GOALS, new GoalMap());
    }

    public static UnhingedScavengerObjective of(float objectiveProbability, Config config, ResourceLocation pool) {
        return new UnhingedScavengerObjective(objectiveProbability, config, pool);
    }


    @Override
    public void initServer(VirtualWorld world, Vault vault) {
        CommonEvents.OBJECTIVE_PIECE_GENERATION.register(this, (data) -> {
            if (data.getVault() == vault) {
                this.ifPresent(OBJECTIVE_PROBABILITY, probability -> data.setProbability(probability));
            }
        });
        this.registerObjectiveTemplate(world, vault);

        CommonEvents.BLOCK_SET.at(BlockSetEvent.Type.RETURN).in(world).register(this, (data) -> {
            PartialTile target = PartialTile.of(PartialBlockState.of(ModBlocks.PLACEHOLDER), PartialCompoundNbt.empty());
            target.getState().set(PlaceholderBlock.TYPE, PlaceholderBlock.Type.OBJECTIVE);
            if (target.isSubsetOf(PartialTile.of(data.getState()))) {
                data.getWorld().setBlock(data.getPos(), ModBlocks.SCAVENGER_ALTAR.defaultBlockState(), 3);
            }

        });
        CommonEvents.SCAVENGER_ALTAR_CONSUME.register(this, (data) -> {
            if (data.getLevel() == world && ((ScavengerAltarTileEntity)data.getTile()).getItemPlacedBy() != null) {
                Listener listener = ((Listeners)vault.get(Vault.LISTENERS)).get(((ScavengerAltarTileEntity)data.getTile()).getItemPlacedBy());
                if (listener instanceof Runner) {
                    BlockState state = data.getTile().getBlockState();
                    if (state.getBlock() == ModBlocks.DIVINE_ALTAR) {
                        Item patt5959$temp = ((ScavengerAltarTileEntity)data.getTile()).getHeldItem().getItem();
                        if (patt5959$temp instanceof KeystoneItem) {
                            KeystoneItem keystone = (KeystoneItem)patt5959$temp;
                            if (keystone.getGod() != state.getValue(DivineAltarBlock.GOD)) {
                                return;
                            }
                        } else {
                            if (!(((ScavengerAltarTileEntity)data.getTile()).getHeldItem().getItem() instanceof GodBlessingItem)) {
                                return;
                            }

                            if (GodBlessingItem.getGod(((ScavengerAltarTileEntity)data.getTile()).getHeldItem()) != state.getValue(DivineAltarBlock.GOD)) {
                                return;
                            }
                        }
                    }

                    boolean creative = (Boolean)listener.getPlayer().map(ServerPlayer::isCreative).orElse(false);
                    CompoundTag nbt = ((ScavengerAltarTileEntity)data.getTile()).getHeldItem().getTag();
                    if (creative || nbt != null && nbt.getString("VaultId").equals(((UUID)vault.get(Vault.ID)).toString())) {
                        List<ScavengerGoal> goals = (List)((GoalMap)this.get(GOALS)).get(listener.get(Listener.ID));
                        Iterator var9 = goals.iterator();

                        while(var9.hasNext()) {
                            ScavengerGoal goal = (ScavengerGoal)var9.next();
                            goal.consume(((ScavengerAltarTileEntity)data.getTile()).getHeldItem());
                        }

                    }
                }
            }
        });
        Iterator var3 = ((Config)this.getOr(CONFIG, Config.DEFAULT)).get().getTasks().iterator();

        while(var3.hasNext()) {
            ScavengeTask task = (ScavengeTask)var3.next();
            task.initServer(world, vault, this);
        }

        ((ObjList)this.get(CHILDREN)).forEach((child) -> {
            child.initServer(world, vault);
        });
    }

    @Override
    public void tickListener(VirtualWorld world, Vault vault, Listener listener) {
        if (listener instanceof Runner && listener.getPriority(this) < 0) {
            listener.addObjective(vault, this);
            this.generateGoal(vault, listener);
        }

        ScavengerGoal.ObjList goal = (ScavengerGoal.ObjList)((GoalMap)this.get(GOALS)).get(listener.get(Listener.ID));
        if (goal != null && goal.areAllCompleted()) {
            ((ObjList)this.get(CHILDREN)).forEach((child) -> {
                child.tickListener(world, vault, listener);
            });
        }
    }

    private void generateGoal(Vault vault, Listener listener) {
        ScavengerGoal.ObjList list = new ScavengerGoal.ObjList();
        this.get(GOALS).put(listener.get(Listener.ID), list);
        JavaRandom random = JavaRandom.ofInternal(vault.get(Vault.SEED) ^ listener.get(Listener.ID).getMostSignificantBits());
        list.addAll((this.getOr(CONFIG, UnhingedScavengerObjective.Config.DEFAULT)).get().generateGoals(this.getOr(ENTRY_POOL, VaultMod.id("default")), vault.get(Vault.LEVEL).get(), random));
    }

    static {
        ENTRY_POOL = FieldKey.of("entry_pool", ResourceLocation.class).with(Version.v1_46, Adapters.IDENTIFIER, DISK.all()).register(FIELDS);
    }




    public static enum Config {
        DEFAULT(() -> {
            return xyz.iwolfking.woldsvaults.init.ModConfigs.UNHINGED_SCAVENGER;
        }),
        DIVINE_PARADOX(() -> {
            return ModConfigs.DIVINE_PARADOX;
        });

        private Supplier<UnhingedScavengerConfig> supplier;

        private Config(Supplier supplier) {
            this.supplier = supplier;
        }

        public UnhingedScavengerConfig get() {
            return (UnhingedScavengerConfig)this.supplier.get();
        }
    }
}
