package xyz.iwolfking.woldsvaultsstandalone.configs;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.world.item.Items;

public class SupplyBoxConfig extends Config {
    @Expose
    public WeightedList<ProductEntry> POOL = new WeightedList();
    /*    */
    /*    */
    /*    */   public String getName() {
        /* 14 */     return "supply_box";
        /*    */   }
    /*    */
    /*    */
    /*    */   protected void reset() {
        /* 19 */     this.POOL.add(new ProductEntry(Items.APPLE, 8, null), 3);
        /* 20 */     this.POOL.add(new ProductEntry(Items.GOLDEN_APPLE, 1, null), 1);
        /*    */   }
}
