package xyz.iwolfking.woldsvaults.mixin;

import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ThrownTrident.class)
public interface ThrownTridentAccessor {
    @Accessor ItemStack getTridentItem();
}
