package xyz.iwolfking.woldsvaults.mixin.the_vault.custom;

import iskallia.vault.item.InfusedCatalystItem;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.crystal.properties.CapacityCrystalProperties;
import iskallia.vault.recipe.anvil.AnvilContext;
import iskallia.vault.recipe.anvil.CatalystAnvilRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.woldsvaults.api.util.PrestigePowerHelper;
import xyz.iwolfking.woldsvaults.prestige.CrystalSizePrestigePower;

import java.util.List;


@Mixin(value = CatalystAnvilRecipe.class, remap = false)
public class MixinCatalystAnvilRecipe {
    @Inject(method = "onSimpleCraft", at = @At("RETURN"))
    private void maybeRefundCapacityCostOnTake(AnvilContext context, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            return;
        }

        Player player = context.getPlayer().orElse(null);
        if (!(player instanceof ServerPlayer)) {
            return;
        }

        Integer catalystSize = InfusedCatalystItem.getSize(context.getInput()[1]).orElse(null);
        if (catalystSize == null || catalystSize <= 0) {
            return;
        }

        context.onTake(context.getTake().append(() -> {
            if (shouldNotConsumeCapacity(player)) {
                refundCapacityCost(context.getOutput(), catalystSize);
            }
        }));
    }

    private static void refundCapacityCost(ItemStack output, int catalystSize) {
        if (output.isEmpty()) {
            return;
        }

        CrystalData data = CrystalData.read(output);
        if (!(data.getProperties() instanceof CapacityCrystalProperties properties)) {
            return;
        }

        properties.setSize(Math.max(0, properties.getSize() - catalystSize));
        data.write(output);
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
