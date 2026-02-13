package xyz.iwolfking.woldsvaults.util;


import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.tree.TalentTree;
import iskallia.vault.world.data.PlayerTalentsData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.woldsvaults.abilities.CritHitChanceTalent;
import xyz.iwolfking.woldsvaults.abilities.CritHitDamageTalent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = "woldsvaultsstandalone")
public class CritTalentHandler {

    private static final Random RNG = new Random();
    private static final String FORCED_CRIT_TAG = "forced_crit";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void criticalHitChance(CriticalHitEvent event){
        Player player = event.getPlayer();
        if(!(player instanceof ServerPlayer sp)) return;

        if(!(event.getTarget() instanceof LivingEntity)) return;

        float critHitChance = getCritChance(sp);
        boolean isVanillaCrit = event.isVanillaCritical();
        sp.getPersistentData().putBoolean(FORCED_CRIT_TAG, false);

        if (isVanillaCrit) return;
        if (critHitChance > 0f && roll(critHitChance)) {
            sp.getPersistentData().putBoolean(FORCED_CRIT_TAG, true);
            event.setResult(net.minecraftforge.eventbus.api.Event.Result.ALLOW);

            // Visuals
            if (sp.level instanceof ServerLevel sl) {
                LivingEntity target = (LivingEntity) event.getTarget();

                sl.sendParticles(
                        ParticleTypes.CRIT,
                        target.getX(), target.getY() + 1.0D, target.getZ(), 12, 0.3D, 0.4D, 0.3D, 0.1D);

                sl.playSound(
                        null, target.blockPosition(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void criticalHitDamage(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer sp)) return;

        boolean forcedCrit = sp.getPersistentData().getBoolean(FORCED_CRIT_TAG);

        boolean vanillaCrit = sp.fallDistance > 0.0F
                && !sp.isOnGround()
                && !sp.onClimbable()
                && !sp.isInWater()
                && !sp.isPassenger()
                && sp.getEffect(net.minecraft.world.effect.MobEffects.BLINDNESS) == null;

        boolean isCrit = forcedCrit || vanillaCrit;
        if (!isCrit) return;

        float critDamageBonus = getCritDamage(sp);
        float baseCritMultiplier = 1.5f; // vanilla crit is 1.5x

        float finalMultiplier = baseCritMultiplier * (1.0f + Math.max(0f, critDamageBonus));

        event.setAmount(event.getAmount() * finalMultiplier);

        if (forcedCrit) sp.getPersistentData().putBoolean(FORCED_CRIT_TAG, false);
    }



    private static float getCritChance(ServerPlayer player){
        TalentTree talents = PlayerTalentsData.get((ServerLevel) player.getLevel()).getTalents(player);
        float chance = 0.0f;
        for (CritHitChanceTalent critHitChanceTalent: talents.getAll(CritHitChanceTalent.class, Skill::isUnlocked)){
            chance = Math.max(chance, critHitChanceTalent.getPercentCritHitChance());
        }
        return chance;
    }
    private static float getCritDamage(ServerPlayer player){
        TalentTree talents = PlayerTalentsData.get((ServerLevel) player.getLevel()).getTalents(player);
        float damage = 0.0f;
        for (CritHitDamageTalent critHitDamageTalent: talents.getAll(CritHitDamageTalent.class, Skill::isUnlocked)){
            damage = Math.max(damage, critHitDamageTalent.getPercentCritHitDamage());
        }
        return damage;
    }
    private static boolean roll(float chance) {
        if (chance <= 0f) return false;
        if (chance >= 1f) return true;
        return RNG.nextFloat() < chance;
    }
}
