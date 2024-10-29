package xyz.iwolfking.woldsvaultsstandalone.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.event.RegistryEvent;
import xyz.iwolfking.woldsvaultsstandalone.WoldsVaults;
import xyz.iwolfking.woldsvaultsstandalone.api.pehkui.CustomScaleTypes;
import xyz.iwolfking.woldsvaultsstandalone.effect.mobeffects.GrowingPotionEffect;
import xyz.iwolfking.woldsvaultsstandalone.effect.mobeffects.ReavingPotionEffect;
import xyz.iwolfking.woldsvaultsstandalone.effect.mobeffects.ShrinkingPotionEffect;


public class ModEffects {
    public static final MobEffect GROWING = new GrowingPotionEffect(MobEffectCategory.NEUTRAL, 0xc90000, CustomScaleTypes.SIZE, new ResourceLocation(WoldsVaults.MODID, "growing"));
    public static final MobEffect SHRINKING = new ShrinkingPotionEffect(MobEffectCategory.NEUTRAL, 0xcca468, CustomScaleTypes.SIZE, new ResourceLocation(WoldsVaults.MODID, "shrinking"));
    public static final MobEffect REAVING = new ReavingPotionEffect(MobEffectCategory.BENEFICIAL, 0xcca468, new ResourceLocation(WoldsVaults.MODID, "reaving"));
    public static void register(RegistryEvent.Register<MobEffect> event) {
        event.getRegistry().registerAll(new MobEffect[]{SHRINKING, GROWING, REAVING});
    }
}
