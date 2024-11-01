package xyz.iwolfking.woldsvaults.models;

import iskallia.vault.VaultMod;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;

public class Plushies {
    public static final DynamicModelRegistry<HandHeldModel> REGISTRY = new DynamicModelRegistry();

    public static final HandHeldModel AXOLOTL;
    public static final HandHeldModel WOLD;
    public static final HandHeldModel IWK;
    public static final HandHeldModel JOSEPH;
    public static final HandHeldModel CAIGAN;
    public static final HandHeldModel SQUISH;
    public static final HandHeldModel ISKALL;
    public static final HandHeldModel JACKAL;
    public static final HandHeldModel DWELLER;
    public static final HandHeldModel HRRY;
    public static final HandHeldModel SPARKLEZ;
    public static final HandHeldModel ARTIC_FOX;
    public static final HandHeldModel BEE;
    public static final HandHeldModel CAT;
    public static final HandHeldModel COW;
    public static final HandHeldModel DOLPHIN;
    public static final HandHeldModel DRAGON;
    public static final HandHeldModel FROG;
    public static final HandHeldModel LLAMA;
    public static final HandHeldModel PANDA;
    public static final HandHeldModel PARROT;
    public static final HandHeldModel SLIME;
    public static final HandHeldModel TURTLE;
    public static final HandHeldModel WARDEN;
    public static final HandHeldModel ARAEVIN;
    public static final HandHeldModel BLAHAJ;

    public Plushies() {
    }

    static {
        AXOLOTL = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/axolotl"), "Aquee the Axolotl")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        WOLD = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/wold"), "Wold the Tyrant")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        IWK = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/wolf"), "Wolf the Creator")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        JOSEPH = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/joseph"), "Joseph the Filterer")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        CAIGAN = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/caigan"), "Caigan the Mouse")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        SQUISH = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/dearliest"), "Dearliest the Squishiest")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        ISKALL = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/iskall"), "Iskall")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        JACKAL = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/jackal"), "Jackal the Legend")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        DWELLER = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/dweller"), "Dave the Dweller")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        HRRY = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/hrry"), "Hrry the Hermit")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        SPARKLEZ = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/sparklez"), "Sparklez the Captain")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        ARTIC_FOX = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/artic_fox"), "Snowflake the Artic Fox")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        BEE = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/bee"), "Billy the Bee")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        CAT = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/cat"), "Carl the Cat")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        COW = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/cow"), "Caroline the Cow")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        DOLPHIN = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/dolphin"), "Daphne the Dolphin")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        DRAGON = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/dragon"), "Drew the Dragon")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        FROG = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/frog"), "Fred the Frog")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        LLAMA = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/llama"), "Larry the Llama")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        PANDA = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/panda"), "Petey the Panda")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        PARROT = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/parrot"), "Petroclus the Parrot")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        SLIME = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/slime"), "Stevey the Slime")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        TURTLE = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/turtle"), "Tim the Turtle")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        WARDEN = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/warden"), "Wally the Warden")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        ARAEVIN = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/araevin"), "Araevin the Heathen")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
        BLAHAJ = (HandHeldModel)REGISTRY.register((HandHeldModel)(new HandHeldModel(VaultMod.id("gear/plushie/blahaj"), "Blahaj the Shark")).properties(new DynamicModelProperties().allowTransmogrification().discoverOnRoll()));
    }
}
