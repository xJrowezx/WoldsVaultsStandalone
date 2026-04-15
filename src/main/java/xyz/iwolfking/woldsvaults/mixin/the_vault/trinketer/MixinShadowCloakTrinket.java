package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.client.data.ClientExpertiseData;
import iskallia.vault.gear.trinket.effects.ShadowCloakTrinket;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.skill.tree.ExpertiseTree;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ShadowCloakTrinket.class, remap = false)
public class MixinShadowCloakTrinket {

    @Redirect(method = "lambda$breakInvisibility$4", at = @At(value = "INVOKE", target = "Liskallia/vault/gear/trinket/effects/ShadowCloakTrinket$Config;getDisableLength()I"))
    private static int reduceResetTime(ShadowCloakTrinket.Config instance, @Local(argsOnly = true) Player p) {
        double enhancement;
        if (p instanceof ServerPlayer player) {
            enhancement = PlayerExpertisesData.get(player.getLevel()).getExpertises(player).getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();
        } else {
            ExpertiseTree tree = ((AccessorClientExpertiseData) new ClientExpertiseData()).getExpertises();
            enhancement = tree.getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();
        }

        return (int) Math.floor(instance.getDisableLength() * (1 + enhancement));
    }
}
