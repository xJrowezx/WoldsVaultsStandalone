package xyz.iwolfking.woldsvaultsstandalone.init.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.quark.addons.oddities.item.BackpackItem;
import xyz.iwolfking.woldsvaultsstandalone.init.ModBlocks;

@Mod.EventBusSubscriber(value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModModels {
    public static void setupRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer((Block) ModBlocks.SURVIVAL_MOB_BARRIER, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer((Block) ModBlocks.ISKALLIAN_LEAVES_BLOCK, RenderType.translucent());
    }


//    @SubscribeEvent
//    public static void registerBlockColorHandles(ColorHandlerEvent.Block event) {
//        event.getBlockColors().register((state, blockDisplayReader, pos, tintIndex) -> {
//            if(tintIndex < 0 || tintIndex > 1 || pos == null) {
//                return -1;
//            }
//            return WorldHelper.getBlockEntity(blockDisplayReader, pos, BackpackBlockEntity.class).map(te -> tintIndex == 0 ? te.getBackpackWrapper().getMainColor() : te.getBackpackWrapper().getAccentColor()).orElse(getDefaultColor(tintIndex));
//        }, ModBlocks.XL_BACKPACK);
//    }
//
//    @SubscribeEvent
//    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
//        event.getItemColors().register((backpack, layer) -> {
//            if(layer > 1 || !(backpack.getItem() instanceof BackpackItem)) {
//                return -1;
//            }
//            return backpack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).map(backpackWrapper -> {
//                if(layer == 0) {
//                    return backpackWrapper.getMainColor();
//                }
//                else if(layer == 1) {
//                    return backpackWrapper.getAccentColor();
//                }
//                return -1;
//            }).orElse(BackpackWrapper.DEFAULT_CLOTH_COLOR);
//        }, ModItems.XL_BACKPACK);
//    }
//
//    public static int getDefaultColor(int tintIndex) {
//        return tintIndex == 0 ? BackpackWrapper.DEFAULT_CLOTH_COLOR : BackpackWrapper.DEFAULT_BORDER_COLOR;
//    }
}
