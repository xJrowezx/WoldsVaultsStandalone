package xyz.iwolfking.woldsvaultsstandalone.mixin.the_vault.recipes;

import iskallia.vault.item.crystal.recipe.AnvilRecipe;
import iskallia.vault.item.crystal.recipe.AnvilRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.woldsvaultsstandalone.recipes.capstone.*;
import xyz.iwolfking.woldsvaultsstandalone.recipes.gear.GearPrefixAdderRecipe;
import xyz.iwolfking.woldsvaultsstandalone.recipes.gear.GearRepairAdderRecipe;
import xyz.iwolfking.woldsvaultsstandalone.recipes.gear.GearSuffixAdderRecipe;
import xyz.iwolfking.woldsvaultsstandalone.recipes.tool.OmegaToolCapacityAdderRecipe;
import xyz.iwolfking.woldsvaultsstandalone.recipes.tool.ToolCapacityAdderRecipe;
import xyz.iwolfking.woldsvaultsstandalone.recipes.tool.ToolModifierNullifierRecipe;
import xyz.iwolfking.woldsvaultsstandalone.recipes.tool.ToolStylishAdderRecipe;

import java.util.List;

@Mixin(value = AnvilRecipes.class, remap = false)
public abstract class MixinAnvilRecipes {


    @Shadow private static List<AnvilRecipe> REGISTRY;

    @Inject(method = "register()V", at = @At("HEAD"))
    private static void register(CallbackInfo ci) {
        woldsVaults$register(new FrenzyCapstoneRecipe());
        woldsVaults$register(new ProsperousCapstoneRecipe());
        woldsVaults$register(new AllSeeingEyeCapstoneRecipe());
        woldsVaults$register(new EnchantedCapstoneRecipe());
        woldsVaults$register(new VendoorCapstoneRecipe());
        woldsVaults$register(new GearPrefixAdderRecipe());
        woldsVaults$register(new GearSuffixAdderRecipe());
        woldsVaults$register(new GearRepairAdderRecipe());
        woldsVaults$register(new ToolCapacityAdderRecipe());
        woldsVaults$register(new OmegaToolCapacityAdderRecipe());
        woldsVaults$register(new ToolModifierNullifierRecipe());
        woldsVaults$register(new ToolStylishAdderRecipe());
    }

    @Unique
    private static <T extends AnvilRecipe> void woldsVaults$register(T recipe) {
        REGISTRY.add(recipe);
    }
}
