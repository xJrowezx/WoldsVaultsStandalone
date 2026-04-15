package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.client.data.ClientExpertiseData;
import iskallia.vault.gear.trinket.effects.BounceTrinket;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.skill.tree.ExpertiseTree;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BounceTrinket.class, remap = false)
public class MixinBounceTrinket {

    @Redirect(method = "onFall", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingFallEvent;getDistance()F"))
    private static float makeBounceHigher(LivingFallEvent instance, @Local(name = "player") Player p) {
        double enhancement;
        if (p instanceof ServerPlayer player) {
            enhancement = PlayerExpertisesData.get(player.getLevel()).getExpertises(player).getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();
        } else {
            ExpertiseTree tree = ((AccessorClientExpertiseData) new ClientExpertiseData()).getExpertises();
            enhancement = tree.getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();
        }

        return (float) (instance.getDistance() * (1 + enhancement));
    }
}
