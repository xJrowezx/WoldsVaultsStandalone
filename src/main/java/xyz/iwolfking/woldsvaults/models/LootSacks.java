package xyz.iwolfking.woldsvaults.models;

import iskallia.vault.VaultMod;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;

public class LootSacks {
    public static final DynamicModelRegistry<HandHeldModel> REGISTRY = new DynamicModelRegistry();

    public static final HandHeldModel BUNDLE;
    public static final HandHeldModel GRAY;
    public static final HandHeldModel GREEN;
    public static final HandHeldModel LIGHT_BLUE;
    public static final HandHeldModel PINK;
    public static final HandHeldModel PURPLE;
    public static final HandHeldModel RED;
    public static final HandHeldModel YELLOW;

    public LootSacks() {
    }

    static {
        BUNDLE = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/loot_sack/bundle"), "Bundle Sack")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        GRAY = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/loot_sack/gray"), "Gray Bundle Sack")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        GREEN = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/loot_sack/green"), "Green Bundle Sack")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        LIGHT_BLUE = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/loot_sack/light_blue"), "Light Blue Bundle Sack")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        PINK = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/loot_sack/pink"), "Pink Bundle Sack")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        PURPLE = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/loot_sack/purple"), "Purple Bundle Sack")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        RED = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/loot_sack/red"), "Red Bundle Sack")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        YELLOW = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/loot_sack/yellow"), "Yellow Bundle Sack")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
    }
}
