package xyz.iwolfking.woldsvaults.mixin.the_vault.custom;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import iskallia.vault.item.crystal.properties.CapacityCrystalProperties;
import iskallia.vault.recipe.anvil.AnvilContext;
import iskallia.vault.recipe.anvil.CatalystAnvilRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.iwolfking.woldsvaults.api.util.PrestigePowerHelper;
import xyz.iwolfking.woldsvaults.prestige.CrystalSizePrestigePower;

import java.util.List;


@Mixin(value = CatalystAnvilRecipe.class, remap = false)
public class MixinCatalystAnvilRecipe {
    @WrapOperation(method = "onSimpleCraft", at = @At(value = "INVOKE", target = "Liskallia/vault/item/crystal/properties/CapacityCrystalProperties;setSize(I)Liskallia/vault/item/crystal/properties/CapacityCrystalProperties;"))
    private CapacityCrystalProperties maybeSkipCapacityCost(CapacityCrystalProperties properties, int newSize, Operation<CapacityCrystalProperties> original, AnvilContext context) {
        Player player = context.getPlayer().orElse(null);


        if (shouldNotConsumeCapacity(player)) {
            return properties;
        }

        return original.call(properties, newSize);
    }

    private static boolean shouldNotConsumeCapacity(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }

        List<CrystalSizePrestigePower> prestigePowers =
                PrestigePowerHelper.getPrestigePowersOfType(serverPlayer, CrystalSizePrestigePower.class);
        if (prestigePowers.isEmpty()) {
            return false;
        }

        float sizeAvoidChance = 0.0f;
        for (CrystalSizePrestigePower power : prestigePowers) {
            sizeAvoidChance = Math.max(sizeAvoidChance, power.getSizeAvoidChance());
        }

        return sizeAvoidChance > 0.0f && serverPlayer.getRandom().nextFloat() < sizeAvoidChance;
    }
}
