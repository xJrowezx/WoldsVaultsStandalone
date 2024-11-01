package xyz.iwolfking.woldsvaults.mixin.the_vault;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.woldsvaults.init.ModConfigs;


@Mixin(iskallia.vault.init.ModConfigs.class)
public class MixinModConfigs {

    @Inject(method = "register", at = @At("TAIL"), remap = false)
    private static void onReloadConfigs(CallbackInfo ci) {
        ModConfigs.register();
    }
}
