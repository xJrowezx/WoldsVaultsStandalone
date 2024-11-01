package xyz.iwolfking.woldsvaults.mixin.infernalmobs;


import atomicstryker.infernalmobs.common.InfernalMobsCore;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = InfernalMobsCore.class, remap = false)
public abstract class MixinInfernalMobsCore {


    /**
     * @author iwolfking
     * @reason Disable Infernal Mob spawns
     */
    @Overwrite
    public void processEntitySpawn(LivingEntity entity) {
        return;
    }
}
