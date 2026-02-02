package xyz.iwolfking.woldsvaults.mixin.the_vault.custom;


import iskallia.vault.network.message.ServerboundCompassModeSelectMessage;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ServerboundCompassModeSelectMessage.class, remap = false)
public class MixinCompassPenalty {

    @Inject(
            method = "addCompassPenaltyModifier(Lnet/minecraft/server/level/ServerPlayer;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void noCompassPenalty(ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        // Pretend it "worked" so stacks still increment,
        // but no attribute modifier ever gets applied.
        cir.setReturnValue(true);
    }
}
