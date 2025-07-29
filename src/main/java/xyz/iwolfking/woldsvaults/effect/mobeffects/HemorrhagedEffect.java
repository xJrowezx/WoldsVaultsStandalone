package xyz.iwolfking.woldsvaults.effect.mobeffects;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.iwolfking.woldsvaults.init.ModEffects;

public class HemorrhagedEffect extends MobEffect {
    public static final int STACK_DURATION = 5 * 20;
    public static final int MAX_STACKS = 5;

    public HemorrhagedEffect(MobEffectCategory category, int color, ResourceLocation id) {
        super(category, color);
        this.setRegistryName(id);
    }

    public boolean dealDamage(int duration, int amplifier) {
        return duration % (25 - amplifier * 5) == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level.isClientSide || entity.isDeadOrDying()) {
            return;
        }

        MobEffectInstance instance = entity.getEffect(ModEffects.HEMORRHAGED);
        if (instance != null) {
            if (dealDamage(instance.getDuration(), amplifier)) {
                entity.setHealth(entity.getHealth() - (entity.getMaxHealth() * .01F));
                entity.animateHurt();

                AABB boundingBox = entity.getBoundingBox();
                Vec3 center = boundingBox.getCenter();
                ((ServerLevel) entity.level).sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()),
                        center.x,
                        center.y,
                        center.z,
                        25, // count
                        0, // xOffset
                        boundingBox.getYsize() / 4, // yOffset
                        0, // zOffset
                        0  // speed
                );
            }

            if (instance.getDuration() == 1 && amplifier > 0) {
                entity.removeEffectNoUpdate(ModEffects.HEMORRHAGED);
                entity.addEffect(new MobEffectInstance(
                        instance.getEffect(),
                        STACK_DURATION,
                        --amplifier,
                        instance.isAmbient(),
                        instance.isVisible(),
                        instance.showIcon()
                ));
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == 1 || dealDamage(duration, amplifier);
    }
}
