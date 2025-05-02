package xyz.iwolfking.woldsvaults.mixin;

import iskallia.vault.VaultMod;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.woldsvaults.WoldsVaults;
import xyz.iwolfking.woldsvaults.items.gear.VaultTridentItem;

@Mixin(ThrownTridentRenderer.class)
public abstract class MixinThrownTridentRenderer extends EntityRenderer<ThrownTrident> {

    private static final ResourceLocation trident0 = VaultMod.id("gear/trident/trident_0");


    protected MixinThrownTridentRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/projectile/ThrownTrident;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
    private void getTextureLocation(ThrownTrident trident, CallbackInfoReturnable<ResourceLocation> cir) {
        if(((ThrownTridentAccessor)trident).callGetPickupItem().getItem()  instanceof VaultTridentItem item)  {
            ItemStack tridentStack = ((ThrownTridentAccessor) trident).callGetPickupItem();
            if(item.getDynamicModelId(tridentStack).isPresent()) {
                cir.setReturnValue(ResourceLocation.tryParse(item.getDynamicModelId(tridentStack).get().toString() + ".png"));
            }
        }
    }


}