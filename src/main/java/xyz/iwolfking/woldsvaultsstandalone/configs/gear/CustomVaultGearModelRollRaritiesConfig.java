package xyz.iwolfking.woldsvaultsstandalone.configs.gear;

import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.woldsvaultsstandalone.models.Battlestaffs;
import xyz.iwolfking.woldsvaultsstandalone.models.LootSacks;
import xyz.iwolfking.woldsvaultsstandalone.models.Plushies;
import xyz.iwolfking.woldsvaultsstandalone.models.Tridents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomVaultGearModelRollRaritiesConfig {

    public static final Map<VaultGearRarity, List<String>> BATTLESTAFF_MODEL_ROLLS = new HashMap<>();
    public static final Map<VaultGearRarity, List<String>> TRIDENT_MODEL_ROLLS = new HashMap<>();
    public static final Map<VaultGearRarity, List<String>> PLUSHIE_MODEL_ROLLS = new HashMap<>();
    public static final Map<VaultGearRarity, List<String>> LOOT_SACKS_MODEL_ROLLS = new HashMap<>();

    static {
        BATTLESTAFF_MODEL_ROLLS.put(VaultGearRarity.SCRAPPY, (List<String>) Battlestaffs.REGISTRY
                .getIds().stream()
                .map(ResourceLocation::toString)
                .collect(Collectors.toList()));
        TRIDENT_MODEL_ROLLS.put(VaultGearRarity.SCRAPPY, (List<String>) Tridents.REGISTRY.getIds().stream()
                .map(ResourceLocation::toString).collect(Collectors.toList()));
        PLUSHIE_MODEL_ROLLS.put(VaultGearRarity.SCRAPPY, (List<String>) Plushies.REGISTRY.getIds().stream()
                .map(ResourceLocation::toString).collect(Collectors.toList()));
        LOOT_SACKS_MODEL_ROLLS.put(VaultGearRarity.SCRAPPY, (List<String>) LootSacks.REGISTRY.getIds().stream()
                .map(ResourceLocation::toString).collect(Collectors.toList()));
    }


}
