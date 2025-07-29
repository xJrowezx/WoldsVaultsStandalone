package xyz.iwolfking.woldsvaults.effect.mobeffects;

import iskallia.vault.client.render.healthbar.DamageParticleSystem;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import xyz.iwolfking.woldsvaults.init.ModEffects;

public class HemorrhagedEffect extends MobEffect {
    public static final int COLOR = 0x6e0000;
    public static final int STACK_DURATION = 5 * 20;
    public static final int MAX_STACKS = 5;

    public static final DamageSource DAMAGE_SOURCE = new DamageSource("hemorrhaged");

    public HemorrhagedEffect(MobEffectCategory category, int color, ResourceLocation id) {
        super(category, color);
        this.setRegistryName(id);
    }

    public boolean dealsDamage(int duration, int amplifier) {
        return duration % Math.max(1, 25 - amplifier * 5) == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level.isClientSide || entity.isDeadOrDying()) {
            return;
        }

        MobEffectInstance instance = entity.getEffect(ModEffects.HEMORRHAGED);
        if (instance != null) {
            if (dealsDamage(instance.getDuration(), amplifier)) {
                float damage = entity.getMaxHealth() * .01F;
                entity.setHealth(entity.getHealth() - damage);
                entity.hurt(DAMAGE_SOURCE, 0.0F);
                DamageParticleSystem.addDamageParticle(entity, damage, DamageParticleSystem.DamageType.GENERIC);

                EntityDimensions dimensions = entity.getDimensions(entity.getPose());
                ((ServerLevel) entity.level).sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()),
                        entity.getX() + dimensions.width * 0.5F,
                        entity.getY() + dimensions.height * 0.5F,
                        entity.getZ() + dimensions.width * 0.5F,
                        25, // count
                        0, // xOffset
                        dimensions.height / 4, // yOffset
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
        return duration == 1 || dealsDamage(duration, amplifier);
    }
}
