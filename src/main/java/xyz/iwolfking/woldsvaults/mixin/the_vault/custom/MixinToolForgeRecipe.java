package xyz.iwolfking.woldsvaults.mixin.the_vault.custom;

import iskallia.vault.container.oversized.OverSizedItemStack;
import iskallia.vault.skill.prestige.helper.PrestigeHelper;
import iskallia.vault.gear.crafting.recipe.ToolForgeRecipe;
import iskallia.vault.gear.data.ToolGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.ToolItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import xyz.iwolfking.woldsvaults.api.util.PrestigePowerHelper;
import xyz.iwolfking.woldsvaults.prestige.ToolCapacityPrestigePower;

@Mixin(value = ToolForgeRecipe.class, remap = false)
public class MixinToolForgeRecipe {
    @Inject(method = "createOutput", at = @At("RETURN"), cancellable = true)
    private void addToolCapacityFromPrestige(java.util.List<OverSizedItemStack> consumed, ServerPlayer crafter, int vaultLevel, CallbackInfoReturnable<ItemStack> cir) {
        int extraCapacity = getTotalServerPrestigeCapacity(crafter);
        if (extraCapacity <= 0) {
            return;
        }

        ItemStack toolStack = cir.getReturnValue();
        if (toolStack.getItem() instanceof ToolItem) {
            VaultGearData gear = ToolGearData.read(toolStack);
            int toolCapacity = 0;
            if (gear.getFirstValue(ModGearAttributes.TOOL_CAPACITY).isPresent()) {
                toolCapacity = gear.getFirstValue(ModGearAttributes.TOOL_CAPACITY).get();
            }

            gear.createOrReplaceAttributeValue(ModGearAttributes.TOOL_CAPACITY, toolCapacity + extraCapacity);
            gear.write(toolStack);
            cir.setReturnValue(toolStack);
        }
    }

    @ModifyVariable(method = "addCraftingDisplayTooltip", at = @At(value = "STORE"), ordinal = 0)
    private int addPrestigeCapacityToTooltip(int originalCapacity) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return originalCapacity;
        }

        int extraCapacity = getTotalClientPrestigeCapacity();
        if (extraCapacity <= 0) {
            return originalCapacity;
        }

        return originalCapacity + extraCapacity;
    }

    private static int getTotalServerPrestigeCapacity(ServerPlayer crafter) {
        int extraCapacity = 0;
        for (ToolCapacityPrestigePower power : PrestigePowerHelper.getPrestigePowersOfType(crafter, ToolCapacityPrestigePower.class)) {
            extraCapacity += power.getCapacityIncrease();
        }
        return extraCapacity;
    }

    private static int getTotalClientPrestigeCapacity() {
        int[] extraCapacity = {0};
        PrestigeHelper.getClientPrestige().iterate(ToolCapacityPrestigePower.class,
                power -> extraCapacity[0] += power.getCapacityIncrease());
        return extraCapacity[0];
    }
}
