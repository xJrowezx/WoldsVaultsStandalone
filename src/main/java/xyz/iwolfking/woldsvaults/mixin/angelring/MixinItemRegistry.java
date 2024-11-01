package xyz.iwolfking.woldsvaults.mixin.angelring;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.item.thermal.HardenedAngelRing;
import dev.denismasterherobrine.angelring.item.thermal.LeadstoneAngelRing;
import dev.denismasterherobrine.angelring.item.thermal.ReinforcedAngelRing;
import dev.denismasterherobrine.angelring.item.thermal.ResonantAngelRing;
import dev.denismasterherobrine.angelring.item.vanilla.EnergeticAngelRing;
import dev.denismasterherobrine.angelring.item.vanilla.ItemDiamondRing;
import dev.denismasterherobrine.angelring.item.vanilla.ItemRing;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "dev.denismasterherobrine.angelring.AngelRing$RegistryEvents", remap = false)
public abstract class MixinItemRegistry {

    @Shadow
    protected static ResourceLocation location(String name) {
        return null;
    }

    /**
     * @author iwolfking
     * @reason Remove all but Diamond Ring
     */
    @Inject(method = "RegisterItems", at = @At("HEAD"), cancellable = true)
    private static void RegisterItems(RegistryEvent.Register<Item> event, CallbackInfo ci) {
        ci.cancel();
    }
}
