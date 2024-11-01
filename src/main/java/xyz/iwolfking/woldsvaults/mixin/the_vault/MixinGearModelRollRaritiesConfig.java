package xyz.iwolfking.woldsvaults.mixin.the_vault;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.model.armor.ArmorPieceModel;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.init.ModDynamicModels;
import iskallia.vault.item.gear.VaultArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.data.api.CustomGearModelRolls;
import xyz.iwolfking.vhapi.api.registry.VaultGearRegistry;
import xyz.iwolfking.vhapi.api.registry.gear.CustomVaultGearRegistryEntry;
import xyz.iwolfking.woldsvaults.init.ModItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Mixin(value = GearModelRollRaritiesConfig.class, remap = false)
public abstract class MixinGearModelRollRaritiesConfig {

    @Shadow public abstract Map<String, List<String>> getRolls(ItemStack stack);

    @Shadow @Nullable protected abstract VaultGearRarity getForcedTierRarity(ItemStack stack, ResourceLocation modelId);

    @Inject(
            method = {"getRolls"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void getRollsHook(CallbackInfoReturnable<Map<VaultGearRarity, List<String>>> cir, @Local ItemStack stack) {
       for(CustomVaultGearRegistryEntry entry : VaultGearRegistry.customGearRegistry.get()) {
           if(stack.getItem().equals(entry.getRegistryItem())) {
               cir.setReturnValue(entry.getModelRarityMap());
           }
       }
    }
}
