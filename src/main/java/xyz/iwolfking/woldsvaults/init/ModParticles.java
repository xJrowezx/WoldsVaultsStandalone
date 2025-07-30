package xyz.iwolfking.woldsvaults.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.woldsvaults.WoldsVaults;
import xyz.iwolfking.woldsvaults.particle.BloodParticle;

@Mod.EventBusSubscriber(
        modid = WoldsVaults.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ModParticles {
    public static final SimpleParticleType BLOOD_PARTICLE = simple("blood", true);

    private static SimpleParticleType simple(String name, boolean overrideLimiter) {
        SimpleParticleType type = new SimpleParticleType(overrideLimiter);
        type.setRegistryName(WoldsVaults.id(name));
        return type;
    }

    public static void register(RegistryEvent.Register<ParticleType<?>> event) {
        event.getRegistry().registerAll(BLOOD_PARTICLE);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        ParticleEngine particleManager = Minecraft.getInstance().particleEngine;
        particleManager.register(BLOOD_PARTICLE, BloodParticle.Factory::new);
    }
}
