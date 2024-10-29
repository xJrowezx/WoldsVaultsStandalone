package xyz.iwolfking.woldsvaultsstandalone.jei;

import iskallia.vault.init.ModConfigs;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.woldsvaultsstandalone.WoldsVaults;
import xyz.iwolfking.woldsvaultsstandalone.init.ModItems;
import xyz.iwolfking.woldsvaultsstandalone.jei.category.*;


import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
public class WoldsVaultsJeiPlugin implements IModPlugin {

    public WoldsVaultsJeiPlugin() {}
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return WoldsVaults.id("wolds_jei_integration");
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.ENIGMA_EGG), EnigmaEggRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.OMEGA_BOX), OmegaBoxRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.GEM_BOX), GemBoxRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.SUPPLY_BOX), SupplyBoxRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.AUGMENT_BOX), AugmentBoxRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.INSCRIPTION_BOX), InscriptionBoxRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.CATALYST_BOX), CatalystBoxRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULTAR_BOX), VaultarBoxRecipeCategory.RECIPE_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MysteryEggRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MysteryHostileEggRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EnigmaEggRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new OmegaBoxRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new GemBoxRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SupplyBoxRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AugmentBoxRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new InscriptionBoxRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CatalystBoxRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new VaultarBoxRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(MysteryEggRecipeCategory.RECIPE_TYPE, List.of(ModConfigs.MYSTERY_EGG));
        registration.addRecipes(MysteryHostileEggRecipeCategory.RECIPE_TYPE, List.of(ModConfigs.MYSTERY_HOSTILE_EGG));
        registration.addRecipes(EnigmaEggRecipeCategory.RECIPE_TYPE, List.of(xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs.ENIGMA_EGG));
        registration.addRecipes(OmegaBoxRecipeCategory.RECIPE_TYPE, List.of(xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs.OMEGA_BOX));
        registration.addRecipes(GemBoxRecipeCategory.RECIPE_TYPE, List.of(xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs.GEM_BOX));
        registration.addRecipes(SupplyBoxRecipeCategory.RECIPE_TYPE, List.of(xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs.SUPPLY_BOX));
        registration.addRecipes(AugmentBoxRecipeCategory.RECIPE_TYPE, List.of(xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs.AUGMENT_BOX));
        registration.addRecipes(InscriptionBoxRecipeCategory.RECIPE_TYPE, List.of(xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs.INSCRIPTION_BOX));
        registration.addRecipes(CatalystBoxRecipeCategory.RECIPE_TYPE, List.of(xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs.CATALYST_BOX));
        registration.addRecipes(VaultarBoxRecipeCategory.RECIPE_TYPE, List.of(xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs.VAULTAR_BOX));
    }
}
