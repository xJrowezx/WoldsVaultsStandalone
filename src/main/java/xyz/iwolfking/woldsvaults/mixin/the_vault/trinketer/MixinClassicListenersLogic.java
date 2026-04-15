package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.core.vault.player.ClassicListenersLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.stream.DoubleStream;

@Mixin(value = ClassicListenersLogic.class, remap = false)
public class MixinClassicListenersLogic {

    @Redirect(method = "lambda$onJoin$10", at = @At(value = "INVOKE", target = "Ljava/util/stream/DoubleStream;sum()D"))
    private double setDamageAvoidanceChanceToZero(DoubleStream instance) {
        return 0;
    }

}
