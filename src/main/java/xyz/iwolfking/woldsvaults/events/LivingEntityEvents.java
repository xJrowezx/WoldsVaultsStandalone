package xyz.iwolfking.woldsvaults.events;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import iskallia.vault.block.CoinPileBlock;
import iskallia.vault.block.VaultChestBlock;
import iskallia.vault.block.VaultOreBlock;
import iskallia.vault.core.event.CommonEvents;
import iskallia.vault.entity.VaultBoss;
import iskallia.vault.entity.champion.ChampionLogic;
import iskallia.vault.gear.attribute.type.VaultGearAttributeTypeMerger;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.gear.trinket.TrinketHelper;
import iskallia.vault.gear.trinket.effects.MultiJumpTrinket;
import iskallia.vault.snapshot.AttributeSnapshotHelper;
import iskallia.vault.util.calc.PlayerStat;
import iskallia.vault.util.calc.ThornsHelper;
import iskallia.vault.world.data.ServerVaults;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.woldsvaults.WoldsVaults;
import xyz.iwolfking.woldsvaults.api.helper.WoldAttributeHelper;
import xyz.iwolfking.woldsvaults.configs.core.WoldsVaultsConfig;
import xyz.iwolfking.woldsvaults.data.HexEffects;
import xyz.iwolfking.woldsvaults.init.ModEffects;
import xyz.iwolfking.woldsvaults.init.ModGearAttributes;
import xyz.iwolfking.woldsvaults.items.gear.VaultLootSackItem;
import xyz.iwolfking.woldsvaults.items.gear.VaultPlushieItem;
import xyz.iwolfking.woldsvaults.util.WoldEventHelper;
import xyz.iwolfking.woldsvaults.util.WoldEventUtils;

@Mod.EventBusSubscriber(
        modid = "woldsvaultsstandalone"
)
public class LivingEntityEvents {

    private static SoundEvent ANCHOR_SLAM_SOUND = null;
    private static SoundEvent ALTERNATIVE_SOUND = null;

    public static void init() {
         ANCHOR_SLAM_SOUND  = Registry.SOUND_EVENT.get(new ResourceLocation("bettercombat:anchor_slam"));
         ALTERNATIVE_SOUND  = SoundEvents.ANVIL_HIT;
                 //Registry.SOUND_EVENT.get(new ResourceLocation("bettercombat:anchor_slam"));
    }

    @SubscribeEvent
    public static void capLightningDamageInVaults(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof Player player && event.getSource().equals(DamageSource.LIGHTNING_BOLT)) {
            ServerVaults.get(player.level).ifPresent(vault -> {
                event.setAmount( Math.min(event.getAmount(), player.getMaxHealth() * 0.1F));
                //player.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(), 80, 0));
            });
        }
    }

    @SubscribeEvent
    public static void reavingDamage(LivingHurtEvent event) {
        //Prevent an entity from being reaved more than once or applying to non-melee strikes.
        if(event.getEntityLiving().hasEffect(ModEffects.REAVING) || !WoldEventUtils.isNormalAttack()) {
            return;
        }


        if(event.getSource().getEntity() instanceof Player player && player.getMainHandItem().getItem() instanceof VaultGearItem) {
            VaultGearData data = VaultGearData.read(player.getMainHandItem().copy());
            if(data != null && data.hasAttribute(ModGearAttributes.REAVING_DAMAGE)) {
                event.getEntityLiving().addEffect(new MobEffectInstance(ModEffects.REAVING, Integer.MAX_VALUE, 0));
                event.getEntityLiving().addEffect(new MobEffectInstance(iskallia.vault.init.ModEffects.NO_AI, 20, 0));

                if(ChampionLogic.isChampion(event.getEntityLiving()) || InfernalMobsCore.getMobModifiers(event.getEntityLiving()) != null || event.getEntityLiving() instanceof VaultBoss) {
                    event.setAmount(event.getAmount() + (event.getEntityLiving().getMaxHealth() * (data.get(ModGearAttributes.REAVING_DAMAGE, VaultGearAttributeTypeMerger.floatSum()) * 0.5F)));
                }
                else {
                    event.setAmount(event.getAmount() + (event.getEntityLiving().getMaxHealth() * data.get(ModGearAttributes.REAVING_DAMAGE, VaultGearAttributeTypeMerger.floatSum())));
                }

                if(ANCHOR_SLAM_SOUND == null) {
                    player.getLevel().playSound(null, event.getEntity(), ALTERNATIVE_SOUND, SoundSource.PLAYERS, 1.0F, 1.0F);
                    return;
                }
                player.getLevel().playSound(null, event.getEntity(), ANCHOR_SLAM_SOUND, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    @SubscribeEvent
    public static void executionDamage(LivingHurtEvent event) {
        //Prevent an entity from being reaved more than once or applying to non-melee strikes.
        if(!WoldEventHelper.isNormalAttack()) {
            return;
        }

        if(event.getSource().isProjectile()) {
            return;
        }

        if(event.getSource().getEntity() instanceof Player player && player.getMainHandItem().getItem() instanceof VaultGearItem) {
            VaultGearData data = VaultGearData.read(player.getMainHandItem().copy());
            if(data != null && data.hasAttribute(ModGearAttributes.EXECUTION_DAMAGE)) {
                event.setAmount(event.getAmount() + ((event.getEntityLiving().getMaxHealth() - event.getEntityLiving().getHealth()) * data.get(ModGearAttributes.EXECUTION_DAMAGE, VaultGearAttributeTypeMerger.floatSum())));
            }
        }
    }

    @SubscribeEvent
    public static void thornsScalingDamage(LivingHurtEvent event) {
        //Prevent an entity from being reaved more than once or applying to non-melee strikes.
        if(!WoldEventHelper.isNormalAttack()) {
            return;
        }

        if(event.getSource().isProjectile()) {
            return;
        }

        if(event.getSource().getEntity() instanceof Player player && player.getMainHandItem().getItem() instanceof VaultGearItem) {
            VaultGearData data = VaultGearData.read(player.getMainHandItem().copy());
            if(data != null) {
                float thornsScalingPercent = AttributeSnapshotHelper.getInstance().getSnapshot(player).getAttributeValue(ModGearAttributes.THORNS_SCALING_DAMAGE, VaultGearAttributeTypeMerger.floatSum());
                if(thornsScalingPercent <= 0F) {
                    return;
                }

                float thornsDamage = ThornsHelper.getAdditionalThornsFlatDamage(player);
                event.setAmount(event.getAmount() + (thornsDamage * thornsScalingPercent));

            }
        }
    }

    @SubscribeEvent
    public static void apScalingDamage(LivingHurtEvent event) {
        //Prevent an entity from being reaved more than once or applying to non-melee strikes.
        if(!WoldEventHelper.isNormalAttack()) {
            return;
        }

        if(event.getSource().isProjectile()) {
            return;
        }

        if(event.getSource().getEntity() instanceof Player player && player.getMainHandItem().getItem() instanceof VaultGearItem) {
            VaultGearData data = VaultGearData.read(player.getMainHandItem().copy());
            if(data != null) {
                float apScalingPercent = AttributeSnapshotHelper.getInstance().getSnapshot(player).getAttributeValue(ModGearAttributes.AP_SCALING_DAMAGE, VaultGearAttributeTypeMerger.floatSum());
                if(apScalingPercent <= 0F) {
                    return;
                }

                float abilityPower = WoldAttributeHelper.getAdditionalAbilityPower(player);
                event.setAmount(event.getAmount() + (abilityPower * apScalingPercent));

            }
        }
    }

    @SubscribeEvent
    public static void hexingHit(LivingHurtEvent event) {
        //Prevent an entity from being reaved more than once or applying to non-melee strikes.
        if(!WoldEventHelper.isNormalAttack()) {
            return;
        }

        if(event.getSource().isProjectile()) {
            return;
        }

        if(event.getSource().getEntity() instanceof Player player && player.getMainHandItem().getItem() instanceof VaultGearItem) {
            VaultGearData data = VaultGearData.read(player.getMainHandItem().copy());
            if(data.hasAttribute(ModGearAttributes.HEXING_CHANCE)) {
                if(player.level.random.nextFloat() <= data.get(ModGearAttributes.HEXING_CHANCE, VaultGearAttributeTypeMerger.floatSum())) {
                    MobEffectInstance instance = HexEffects.HEX_EFFECTS.getRandom(player.getRandom());
                    if(instance == null){
                        return;
                    }

                    event.getEntityLiving().addEffect(new MobEffectInstance(instance));
                }
            }
        }
    }



    @SubscribeEvent(
            priority = EventPriority.LOW
    )
    public static void onDamageTotem(LivingHurtEvent event) {
        Level world = event.getEntity().getCommandSenderWorld();
        if (!world.isClientSide() && world instanceof ServerLevel) {
            LivingEntity var3 = event.getEntityLiving();
            if (var3 instanceof Player) {
                Player player = (Player)var3;
                if (!event.getSource().isBypassArmor()) {
                    ItemStack offHand = event.getEntityLiving().getOffhandItem();
                    if (!ServerVaults.get(world).isEmpty() || !(offHand.getItem() instanceof VaultGearItem)) {
                        if (offHand.getItem() instanceof VaultPlushieItem) {
                            int damage = (int) CommonEvents.PLAYER_STAT.invoke(PlayerStat.DURABILITY_DAMAGE, player, Math.max(1.0F, event.getAmount() / 6.0F)).getValue();
                            if (damage <= 1) {
                                damage = 1;
                            }

                            offHand.hurtAndBreak(damage, event.getEntityLiving(), (entity) -> {
                                entity.broadcastBreakEvent(EquipmentSlot.OFFHAND);
                            });
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(
            priority = EventPriority.LOW
    )
    public static void onBreakVaultBlock(BlockEvent.BreakEvent event) {

        Level world = event.getPlayer().getCommandSenderWorld();
        if (!world.isClientSide() && world instanceof ServerLevel) {
            LivingEntity var3 = event.getPlayer();
            if (var3 instanceof Player player) {
                if (event.getState().getBlock() instanceof VaultChestBlock || event.getState().getBlock() instanceof CoinPileBlock || event.getState().getBlock() instanceof VaultOreBlock) {
                    ItemStack offHand = event.getPlayer().getOffhandItem();
                    if (!ServerVaults.get(world).isEmpty() || !(offHand.getItem() instanceof VaultGearItem)) {
                        if (offHand.getItem() instanceof VaultLootSackItem) {
                            int damage = (int)CommonEvents.PLAYER_STAT.invoke(PlayerStat.DURABILITY_DAMAGE, player, 1.0F).getValue();
                            if (damage <= 1) {
                                damage = 1;
                            }

                            offHand.hurtAndBreak(damage, event.getPlayer(), (entity) -> {
                                entity.broadcastBreakEvent(EquipmentSlot.OFFHAND);
                            });
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles the reduction/cancellation of fall damage for players with the Feather Trinket equipped.
     * <p>
     * If the player has an active and usable {@code MultiJumpTrinket}:
     * - Fall damage is canceled if the fall distance is less than 5.0 blocks to prevent damage ticking when taking no damage.
     * - Otherwise, the fall distance is reduced by 2.0 blocks to mitigate fall damage.
     * <p>
     * Called whenever a LivingEntity falls
     */
//    @SubscribeEvent
//    public static void multiJumpTrinketFallReductionEvent(LivingFallEvent event) {
//        if(event.getEntityLiving() instanceof ServerPlayer player) {
//            if (TrinketHelper.getTrinkets(player, MultiJumpTrinket.class).stream().anyMatch(trinket -> trinket.isUsable(player))) {
//                if(event.getDistance() < 5.0F) {
//                    event.setCanceled(true);
//                } else {
//                    event.setDistance(event.getDistance() - 2.0F);
//                }
//            }
//        }
//    }

}
