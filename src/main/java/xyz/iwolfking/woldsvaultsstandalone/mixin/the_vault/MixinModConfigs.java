package xyz.iwolfking.woldsvaultsstandalone.mixin.the_vault;

import iskallia.vault.init.ModContainers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs;


@Mixin(iskallia.vault.init.ModConfigs.class)
public class MixinModConfigs {

    @Inject(method = "register", at = @At("TAIL"), remap = false)
    private static void onReloadConfigs(CallbackInfo ci) {
        ModConfigs.register();
    }
}
