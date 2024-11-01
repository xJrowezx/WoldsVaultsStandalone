package xyz.iwolfking.woldsvaults.models;

import iskallia.vault.VaultMod;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;

public class Tridents {
    public static final DynamicModelRegistry<HandHeldModel> REGISTRY = new DynamicModelRegistry();
    public static final HandHeldModel GUARDIAN;
    public static final HandHeldModel TRIDENT_PITCHFORK;
    public static final HandHeldModel TRIDENT_SEABIRD;

    public static final HandHeldModel TRIDENT_CRIMSON;
    public static final HandHeldModel AXOLOTL;
    public static final HandHeldModel CRYSTAL;
    public static final HandHeldModel EMERALD;
    public static final HandHeldModel GOLDEN;

    public static final HandHeldModel ORANGE;
    public static final HandHeldModel PHANTOMGUARD;
    public static final HandHeldModel PINK;
    public static final HandHeldModel PRISMARINE;
    public static final HandHeldModel PURPLE;


    public static final HandHeldModel ROYAL;
    public static final HandHeldModel CLASSIC;
    public static final HandHeldModel SCRAPPY_1;
    public static final HandHeldModel SCRAPPY_2;
    public static final HandHeldModel SCRAPPY_3;
    public static final HandHeldModel SCRAPPY_4;


    public Tridents() {
    }

    static {
        SCRAPPY_4 = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/scrappy_4"), "Scrappy Trident 4")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        SCRAPPY_3 = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/scrappy_3"), "Scrappy Trident 3")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        SCRAPPY_2 = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/scrappy_2"), "Scrappy Trident 2")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        SCRAPPY_1 = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/scrappy_1"), "Scrappy Trident 1")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        CLASSIC = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/vanilla"), "Classic Vanilla Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        GUARDIAN = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/guardian"), "Guardian Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        TRIDENT_PITCHFORK = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/pitchfork"), "Pitchfork Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        TRIDENT_SEABIRD = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/seabird"), "Seabird Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        TRIDENT_CRIMSON = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/crimson"), "Crimson Lance Trident")).properties((new DynamicModelProperties()).allowTransmogrification().discoverOnRoll()));
        AXOLOTL = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/axolotl"), "Axolotl Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        CRYSTAL = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/crystal"), "Crystal Lance")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        EMERALD = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/emerald"), "Emerald Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        GOLDEN = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/golden"), "Golden Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        PRISMARINE = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/prismarine"), "Prismarine Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        PURPLE = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/purple"), "Purple Trident")).properties((new DynamicModelProperties()).allowTransmogrification().discoverOnRoll()));
        ROYAL = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/royal"), "Royal Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        ORANGE = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/orange"), "Orange Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        PINK = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/pink"), "Pink Trident")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        PHANTOMGUARD = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/trident/phantomguard"), "Phantomguard Lance")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
    }
}
