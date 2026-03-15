package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.client.data.ClientExpertiseData;
import iskallia.vault.gear.trinket.effects.ExplosionBlockPreventionTrinket;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.skill.tree.ExpertiseTree;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ExplosionBlockPreventionTrinket.class, remap = false)
public class MixinExplosionBlockPreventionTrinket {

    @Redirect(method = "onLivingHurt", at = @At(value = "INVOKE", target = "Liskallia/vault/gear/trinket/effects/ExplosionBlockPreventionTrinket$Config;getChance()F"))
    private static float enhancePreventionChance(ExplosionBlockPreventionTrinket.Config instance, @Local(argsOnly = true) LivingAttackEvent event) {
        double enhancement;
        if (event.getEntity() instanceof ServerPlayer player) {
            enhancement = PlayerExpertisesData.get(player.getLevel()).getExpertises(player).getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();
        } else {
            ExpertiseTree tree = ((AccessorClientExpertiseData) new ClientExpertiseData()).getExpertises();
            enhancement = tree.getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();
        }

        return (float) (instance.getChance() + (.5 * enhancement));
    }
}
