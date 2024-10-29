package xyz.iwolfking.woldsvaultsstandalone.objectives;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import iskallia.vault.VaultMod;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.data.key.FieldKey;
import iskallia.vault.core.data.key.SupplierKey;
import iskallia.vault.core.data.key.registry.FieldRegistry;
import iskallia.vault.core.event.ClientEvents;
import iskallia.vault.core.event.CommonEvents;
import iskallia.vault.core.random.ChunkRandom;
import iskallia.vault.core.random.JavaRandom;
import iskallia.vault.core.random.RandomSource;
import iskallia.vault.core.vault.Modifiers;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.core.vault.objective.BingoObjective;
import iskallia.vault.core.vault.objective.Objective;
import iskallia.vault.core.vault.player.Listener;
import iskallia.vault.core.vault.player.Listeners;
import iskallia.vault.core.vault.player.Runner;
import iskallia.vault.core.world.storage.VirtualWorld;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModKeybinds;
import iskallia.vault.task.BingoTask;
import iskallia.vault.task.ProgressConfiguredTask;
import iskallia.vault.task.Task;
import iskallia.vault.task.TaskContext;
import iskallia.vault.task.counter.TargetTaskCounter;
import iskallia.vault.task.counter.TaskCounter;
import iskallia.vault.task.renderer.context.BingoRendererContext;
import iskallia.vault.task.source.EntityTaskSource;
import iskallia.vault.task.source.TaskSource;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BallisticBingoObjective extends BingoObjective {
    public static final SupplierKey<Objective> KEY;
    public static final FieldRegistry FIELDS;
    public static final FieldKey<Task> TASK;
    public static final FieldKey<TaskSource> TASK_SOURCE;
    public static final FieldKey<Integer> JOINED;

    protected BallisticBingoObjective() {
    }

    public static BallisticBingoObjective of(BingoTask task) {
        return (BallisticBingoObjective)(new BallisticBingoObjective()).set(TASK, task);
    }

    public SupplierKey<Objective> getKey() {
        return KEY;
    }

    public FieldRegistry getFields() {
        return FIELDS;
    }

    public TaskContext getContext(VirtualWorld world, Vault vault) {
        this.setIfAbsent(TASK_SOURCE, () -> {
            return EntityTaskSource.ofUuids(JavaRandom.ofInternal((Long)vault.get(Vault.SEED)), new UUID[0]);
        });
        return TaskContext.of((TaskSource)this.get(TASK_SOURCE), world.getServer()).setVault(vault);
    }

    public boolean isCompleted() {
        Object var2 = this.get(TASK);
        boolean var10000;
        if (var2 instanceof BingoTask bingo) {
            if (bingo.areAllCompleted()) {
                var10000 = true;
                return var10000;
            }
        }

        var10000 = false;
        return var10000;
    }

    public int getBingos() {
        Object var2 = this.get(TASK);
        int var10000;
        if (var2 instanceof BingoTask bingo) {
            var10000 = bingo.getCompletedBingos();
        } else {
            var10000 = 0;
        }

        return var10000;
    }

    public void initServer(VirtualWorld world, Vault vault) {
        CommonEvents.LISTENER_JOIN.register(this, (data) -> {
            if (data.getVault() == vault) {
                Listener patt3500$temp = data.getListener();
                if (patt3500$temp instanceof Runner) {
                    Runner runner = (Runner)patt3500$temp;
                    Object patt3573$temp = this.get(TASK_SOURCE);
                    if (patt3573$temp instanceof EntityTaskSource) {
                        EntityTaskSource entitySource = (EntityTaskSource)patt3573$temp;
                        entitySource.add(new UUID[]{runner.getId()});
                    }

                    this.set(JOINED, (Integer)this.getOr(JOINED, 0) + 1);
                }
            }
        });
        CommonEvents.LISTENER_LEAVE.register(this, (data) -> {
            if (data.getVault() == vault) {
                Listener patt3898$temp = data.getListener();
                if (patt3898$temp instanceof Runner) {
                    Runner runner = (Runner)patt3898$temp;
                    Object patt3971$temp = this.get(TASK_SOURCE);
                    if (patt3971$temp instanceof EntityTaskSource) {
                        EntityTaskSource entitySource = (EntityTaskSource)patt3971$temp;
                        entitySource.remove(new UUID[]{runner.getId()});
                    }

                }
            }
        });
        ((Task)this.get(TASK)).onAttach(this.getContext(world, vault));
        CommonEvents.GRID_GATEWAY_UPDATE.register(this, (data) -> {
            if (data.getLevel() == world) {
                data.getEntity().setCompletedBingos(this.getBingos());
            }
        });
        ((ObjList)this.get(CHILDREN)).forEach((child) -> {
            child.initServer(world, vault);
        });
    }

    public void tickServer(VirtualWorld world, Vault vault) {
        if (this.getBingos() > 0) {
            ((ObjList)this.get(CHILDREN)).forEach((child) -> {
                child.tickServer(world, vault);
            });
            if (this.isCompleted()) {
                return;
            }
        }

        Object var4 = this.get(TASK);
        if (var4 instanceof BingoTask root) {
            for(int index = 0; index < root.getWidth() * root.getHeight(); ++index) {
                if (!root.isCompleted(index)) {
                    root.getChild(index).streamSelfAndDescendants(ProgressConfiguredTask.class).forEach((task) -> {
                        TaskCounter patt4993$temp = task.getCounter();
                        if (patt4993$temp instanceof TargetTaskCounter<?, ?> counter) {
                            if (counter.isPopulated()) {
                                counter.get("targetPlayerContribution", Adapters.DOUBLE).ifPresent((contribution) -> {
                                    Object patt5229$temp = counter.getBaseTarget();
                                    if (patt5229$temp instanceof Integer base) {
                                        int additional = Math.max((Integer)this.getOr(JOINED, 0) - 1, 0);
                                        counter.setTarget((int)((double)base + (double)additional * contribution * (double)base));
                                    }

                                });
                                return;
                            }
                        }

                    });
                }
            }
        }

    }

    public void tickListener(VirtualWorld world, Vault vault, Listener listener) {
        if (listener instanceof Runner && listener.getPriority(this) < 0) {
            listener.addObjective(vault, this);
        }

        if (listener instanceof Runner && this.getBingos() > 0) {
            ((ObjList)this.get(CHILDREN)).forEach((child) -> {
                child.tickListener(world, vault, listener);
            });
        }

    }

    public void releaseServer() {
        ((Task)this.get(TASK)).onDetach();
        CommonEvents.release(this);
        ((ObjList)this.get(CHILDREN)).forEach(Objective::releaseServer);
    }

    public void onScroll(Player player, double delta) {
        Object var5 = this.get(TASK);
        if (var5 instanceof BingoTask bingo) {
            bingo.progressBingoLine(player.getUUID(), delta < 0.0 ? 1 : -1);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public void initClient(Vault vault) {
        ClientEvents.MOUSE_SCROLL.register(vault, (event) -> {
            BingoRendererContext context = new BingoRendererContext((PoseStack)null, 0.0F, MultiBufferSource.immediate(Tesselator.getInstance().getBuilder()), Minecraft.getInstance().font);
            if (((Task)this.get(TASK)).onMouseScrolled(event.getScrollDelta(), context)) {
                event.setCanceled(true);
            }

        });
        ((ObjList)this.get(CHILDREN)).forEach((child) -> {
            child.initClient(vault);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public boolean render(Vault vault, PoseStack poseStack, Window window, float partialTicks, Player player) {
        if (this.isCompleted() && (Minecraft.getInstance().screen != null || !ModKeybinds.openBingo.isDown())) {
            boolean rendered = false;

            Objective objective;
            for(Iterator var7 = ((ObjList)this.get(CHILDREN)).iterator(); var7.hasNext(); rendered |= objective.render(vault, poseStack, window, partialTicks, player)) {
                objective = (Objective)var7.next();
            }

            if (rendered) {
                return true;
            }
        }

        BingoRendererContext context = new BingoRendererContext(poseStack, partialTicks, MultiBufferSource.immediate(Tesselator.getInstance().getBuilder()), Minecraft.getInstance().font);
        ((Task)this.get(TASK)).onRender(context);
        return true;
    }

    public boolean isActive(VirtualWorld world, Vault vault, Objective objective) {
        Iterator var4;
        Objective child;
        if (this.isCompleted()) {
            var4 = ((ObjList)this.get(CHILDREN)).iterator();

            do {
                if (!var4.hasNext()) {
                    return false;
                }

                child = (Objective)var4.next();
            } while(!child.isActive(world, vault, objective));

            return true;
        } else {
            if (this.getBingos() > 0) {
                var4 = ((ObjList)this.get(CHILDREN)).iterator();

                while(var4.hasNext()) {
                    child = (Objective)var4.next();
                    if (child.isActive(world, vault, objective)) {
                        return true;
                    }
                }
            }

            return objective == this;
        }
    }
    public void addBingoTaskModifier(Vault vault, String modifierPoolName) {
        ChunkRandom random = ChunkRandom.any();
        TextComponent text = new TextComponent("");
        List<VaultModifier<?>> modifiers = ModConfigs.VAULT_MODIFIER_POOLS.getRandom(VaultMod.id(modifierPoolName), 0, (RandomSource) JavaRandom.ofNanoTime());
        if(!modifiers.isEmpty()) {
            Iterator<VaultModifier<?>> modIter = modifiers.iterator();

            while(modIter.hasNext()) {
                VaultModifier<?> mod = (VaultModifier<?>) modIter.next();
                TextComponent suffix = (TextComponent) mod.getChatDisplayNameComponent(1);
                text.append("The task completion").append((new TextComponent(" added ")).withStyle(ChatFormatting.GRAY)).append(suffix).append((new TextComponent(".")).withStyle(ChatFormatting.GRAY));
                if(modIter.hasNext()) {
                    text.append("\n");
                }
                ((Modifiers)vault.get(Vault.MODIFIERS)).addModifier(mod, 1, true, random);
            }

            for (Listener listener : ((Listeners) vault.get(Vault.LISTENERS)).getAll()) {
                listener.getPlayer().ifPresent((other) -> {
                    other.displayClientMessage(text, false);
                });
            }
        }
    }

    static {
        KEY = (SupplierKey)SupplierKey.of("ballistic_bingo", Objective.class).with(Version.v1_27, BallisticBingoObjective::new);
        FIELDS = (FieldRegistry)Objective.FIELDS.merge(new FieldRegistry());
        TASK = (FieldKey)FieldKey.of("task", Task.class).with(Version.v1_27, Adapters.TASK_NBT, DISK.all().or(CLIENT.all())).register(FIELDS);
        TASK_SOURCE = (FieldKey)FieldKey.of("task_source", TaskSource.class).with(Version.v1_27, Adapters.TASK_SOURCE_NBT, DISK.all().or(CLIENT.all())).register(FIELDS);
        JOINED = (FieldKey)FieldKey.of("joined", Integer.class).with(Version.v1_27, Adapters.INT_SEGMENTED_3, DISK.all().or(CLIENT.all())).register(FIELDS);
    }
}
