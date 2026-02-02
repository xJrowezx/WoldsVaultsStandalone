package xyz.iwolfking.woldsvaults.mixin.the_vault.custom;


import iskallia.vault.network.message.ServerboundCompassModeSelectMessage;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.tree.ExpertiseTree;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.woldsvaults.expertises.PylonPilfererExpertise;

@Mixin(value = ServerboundCompassModeSelectMessage.class, remap = false)
public class MixinCompassPenalty {

    @Inject(
            method = "addCompassPenaltyModifier(Lnet/minecraft/server/level/ServerPlayer;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void noCompassPenalty(ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {


            ExpertiseTree tree = PlayerExpertisesData.get(player.getLevel()).getExpertises(player);
            if(tree == null) {
                return;
            }

            for(Skill skill : tree.skills) {
                if(skill instanceof PylonPilfererExpertise pylonPilfererExpertise) {
                    cir.setReturnValue(true);
                    return;
                }
            }
    }
}
