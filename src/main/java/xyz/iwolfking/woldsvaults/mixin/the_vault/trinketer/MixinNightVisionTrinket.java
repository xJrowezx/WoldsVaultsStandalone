package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.gear.trinket.effects.NightVisionTrinket;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.util.calc.AreaOfEffectHelper;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = NightVisionTrinket.class, remap = false)
public class MixinNightVisionTrinket {

    @Redirect(method = "onWornTick", at = @At(value = "INVOKE", target = "Liskallia/vault/util/calc/AreaOfEffectHelper;adjustAreaOfEffect(Lnet/minecraft/world/entity/LivingEntity;Liskallia/vault/skill/base/Skill;F)F"))
    private float expandAreaOfEffect(LivingEntity entity, Skill attribute, float radius) {
        if (!(entity instanceof ServerPlayer player)) {
            return radius;
        }

        double enhancement = PlayerExpertisesData.get(player.getLevel()).getExpertises(player).getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();

        return AreaOfEffectHelper.adjustAreaOfEffect(entity, attribute, (float) (radius * (1f + enhancement)));
    }
}
