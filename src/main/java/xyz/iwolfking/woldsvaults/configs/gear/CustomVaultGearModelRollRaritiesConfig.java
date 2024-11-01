package xyz.iwolfking.woldsvaults.configs.gear;

import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import xyz.iwolfking.vhapi.api.lib.core.readers.CustomVaultConfigReader;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib.CustomGearModelRollRaritiesConfig;
import xyz.iwolfking.vhapi.api.util.JsonUtils;
import xyz.iwolfking.vhapi.mixin.registry.gear.MixinGearModelRollRaritiesConfig;
import xyz.iwolfking.woldsvaults.WoldsVaults;
import xyz.iwolfking.woldsvaults.init.ModItems;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomVaultGearModelRollRaritiesConfig {

    public static Map<VaultGearRarity, List<String>> BATTLESTAFF_MODEL_ROLLS = new HashMap<>();
    public static Map<VaultGearRarity, List<String>> TRIDENT_MODEL_ROLLS = new HashMap<>();
    public static Map<VaultGearRarity, List<String>> PLUSHIE_MODEL_ROLLS = new HashMap<>();
    public static Map<VaultGearRarity, List<String>> LOOT_SACKS_MODEL_ROLLS = new HashMap<>();

    static {
        BATTLESTAFF_MODEL_ROLLS = getModelMapFromFile(ModItems.BATTLESTAFF);
        TRIDENT_MODEL_ROLLS = getModelMapFromFile(ModItems.TRIDENT);
        PLUSHIE_MODEL_ROLLS = getModelMapFromFile(ModItems.PLUSHIE);
        LOOT_SACKS_MODEL_ROLLS = getModelMapFromFile(ModItems.LOOT_SACK);
    }

    public static Map<VaultGearRarity, List<String>> getModelMapFromFile(Item item) {
        CustomVaultConfigReader<CustomGearModelRollRaritiesConfig> reader = new CustomVaultConfigReader<>();
        try (InputStream stream = WoldsVaults.class.getResourceAsStream("/vhapi_configs/custom_model_rolls/" + item.getRegistryName().getPath() + ".json")) {
            if (stream == null) {
                throw new IOException();
            }
            CustomGearModelRollRaritiesConfig config = reader.readCustomConfig(item.getRegistryName().getPath() ,JsonUtils.parseJsonContentFromStream(stream), CustomGearModelRollRaritiesConfig.class);
            return config.MODEL_ROLLS;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
