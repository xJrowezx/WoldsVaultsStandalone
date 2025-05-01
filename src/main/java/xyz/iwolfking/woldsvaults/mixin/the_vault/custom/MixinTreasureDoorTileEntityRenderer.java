package xyz.iwolfking.woldsvaults.mixin.the_vault.custom;

import iskallia.vault.client.render.TreasureDoorTileEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = TreasureDoorTileEntityRenderer.class, remap = false)
public class MixinTreasureDoorTileEntityRenderer {
    // Create a static Map to store door name replacements
    @Unique
    private static final Map<String, String> DOOR_REPLACEMENTS = new HashMap<>() {{
        put("ISKALLIUM", "SHRIMPIUM");
        put("GORGINITE", "LINATHYST");
        put("BOMIGNITE", "K-TERIUM");
        put("TUBIUM", "TAZANITE");
        put("SPARKLETINE", "ZEEABAR");
        put("ASHIUM", "GIGATONIUM");
        put("UPALINE", "SPIKIUM");
        put("PETZANITE", "JAWWNNITE");
        put("XENIUM", "HOLLADIUM");
    }};

    @Inject(method = "formatDoorTypeName", at = @At("HEAD"), cancellable = true)
    private void fixDisplayName(String doorType, CallbackInfoReturnable<String> cir){
        // Check if the door type is in our replacement map
        if (DOOR_REPLACEMENTS.containsKey(doorType)) {
            cir.setReturnValue(DOOR_REPLACEMENTS.get(doorType));
        }
    }
}