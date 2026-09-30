package xyz.iwolfking.woldsvaults.events;

import iskallia.vault.event.event.CrystalStationModifyEvent;
import iskallia.vault.item.InfusedCatalystItem;
import iskallia.vault.recipe.anvil.AnvilExecutor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.woldsvaults.api.util.CrystalSizePowerHelper;

import java.util.List;

@Mod.EventBusSubscriber(modid = "woldsvaults")
public final class CrystalStationEvents {
    private CrystalStationEvents() {
    }

    /**
     * Crystal size prestige power: each catalyst used on a crystal has a chance to not take up capacity.
     */
    @SubscribeEvent
    public static void onCrystalModified(CrystalStationModifyEvent event) {
        Player player = event.getPlayer();
        ItemStack crystal = event.getCrystal();
        AnvilExecutor.Result result = event.getExecutorResult();
        if (crystal.isEmpty() || result == null) {
            return;
        }

        List<ItemStack> ingredients = event.getIngredients();
        int refundSize = 0;
        for (int slot = 0; slot < ingredients.size(); slot++) {
            if (!result.hasUsedSlot(slot)) {
                continue;
            }

            ItemStack ingredient = ingredients.get(slot);
            if (!(ingredient.getItem() instanceof InfusedCatalystItem)) {
                continue;
            }

            Integer catalystSize = InfusedCatalystItem.getSize(ingredient).orElse(null);
            if (catalystSize != null && catalystSize > 0 && CrystalSizePowerHelper.shouldNotConsumeCapacity(player)) {
                refundSize += catalystSize;
            }
        }

        CrystalSizePowerHelper.refundCapacityCost(crystal, refundSize);
    }
}
