package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.client.data.ClientExpertiseData;
import iskallia.vault.gear.trinket.effects.WallClimbingTrinket;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.skill.tree.ExpertiseTree;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WallClimbingTrinket.class, remap = false)
public class MixinWallClimbingTrinket {

    @Redirect(method = "onTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getSpeed()F"))
    private static float increaseWallClimbingSpeed(Player instance) {
        double enhancement;
        if (instance instanceof ServerPlayer player) {
            enhancement = PlayerExpertisesData.get(player.getLevel()).getExpertises(player).getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();
        } else {
            ExpertiseTree tree = ((AccessorClientExpertiseData) new ClientExpertiseData()).getExpertises();
            enhancement = tree.getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();
        }

        return (float) (instance.getSpeed() * (1 + enhancement));
    }
}
