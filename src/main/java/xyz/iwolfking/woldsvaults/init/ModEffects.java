package xyz.iwolfking.woldsvaults.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.event.RegistryEvent;
import xyz.iwolfking.woldsvaults.WoldsVaults;
import xyz.iwolfking.woldsvaults.abilities.ColossusAbility;
import xyz.iwolfking.woldsvaults.abilities.SneakyGetawayAbility;
import xyz.iwolfking.woldsvaults.api.pehkui.CustomScaleTypes;
import xyz.iwolfking.woldsvaults.effect.mobeffects.*;


public class ModEffects {
    public static final MobEffect GROWING = new GrowingPotionEffect(MobEffectCategory.NEUTRAL, 0xc90000, CustomScaleTypes.SIZE, new ResourceLocation(WoldsVaults.MODID, "growing"));
    public static final MobEffect SHRINKING = new ShrinkingPotionEffect(MobEffectCategory.NEUTRAL, 0xcca468, CustomScaleTypes.SIZE, new ResourceLocation(WoldsVaults.MODID, "shrinking"));
    public static final MobEffect REAVING = new ReavingPotionEffect(MobEffectCategory.BENEFICIAL, 0xcca468, new ResourceLocation(WoldsVaults.MODID, "reaving"));
    public static final MobEffect COLOSSUS = new ColossusAbility.ColossusEffect(MobEffectCategory.BENEFICIAL,0xc90000, CustomScaleTypes.SIZE_NO_MOVEMENT, new ResourceLocation(WoldsVaults.MODID,"colossus"));
    public static final MobEffect SNEAKY_GETAWAY = new SneakyGetawayAbility.SneakyGetawayEffect(MobEffectCategory.BENEFICIAL,0xcca468,CustomScaleTypes.SIZE_NO_MOVEMENT, new ResourceLocation(WoldsVaults.MODID,"sneaky_getaway"));
    public static final MobEffect SAFER_SPACE = new SaferSpacePotionEffect(MobEffectCategory.BENEFICIAL,0xae6bd1, new ResourceLocation(WoldsVaults.MODID,"safer_space"));
    public static final MobEffect LEVITATEII = new LevitateIIPotionEffect(MobEffectCategory.BENEFICIAL,0xceffff, new ResourceLocation(WoldsVaults.MODID,"levitateii"));
    public static final MobEffect HEMORRHAGED = new HemorrhagedEffect(MobEffectCategory.HARMFUL, 0x6e0000, WoldsVaults.id("hemorrhaged"));

    public static void register(RegistryEvent.Register<MobEffect> event) {
        event.getRegistry().registerAll(SHRINKING, GROWING, REAVING, COLOSSUS, SNEAKY_GETAWAY, SAFER_SPACE, LEVITATEII, HEMORRHAGED);
    }
}
