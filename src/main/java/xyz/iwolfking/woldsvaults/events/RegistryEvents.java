package xyz.iwolfking.woldsvaults.events;

import iskallia.vault.gear.modification.GearModification;
import iskallia.vault.init.ModGearModifications;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;
import xyz.iwolfking.woldsvaults.api.pehkui.CustomScaleTypes;
import xyz.iwolfking.woldsvaults.init.*;
import xyz.iwolfking.woldsvaults.init.client.ModModels;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = "woldsvaultsstandalone")
public class RegistryEvents {
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        ModBlocks.registerBlocks(event);
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        ModBlocks.registerBlockItems(event);
    }

    @SubscribeEvent
    public static void onTileEntityRegister(RegistryEvent.Register<BlockEntityType<?>> event) {
        ModBlocks.registerTileEntities(event);
    }

    @SubscribeEvent
    public static void onContainerRegister(RegistryEvent.Register<MenuType<?>> event) {
        ModContainers.register(event);
    }

    //@SubscribeEvent
    //public static void onTrinketRegistry(RegistryEvent.Register<TrinketEffect<?>> event) {
    //    ModTrinkets.init(event);
    //}

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ModBlocks.registerTileEntityRenderers(event);
    }

    @SubscribeEvent
    public static void onParticleRegister(RegistryEvent.Register<ParticleType<?>> event) {
        ModParticles.register(event);
    }

    @SubscribeEvent
    public static void onEffectRegister(RegistryEvent.Register<MobEffect> event) {
        CustomScaleTypes.init();
        ModEffects.register(event);
    }

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityType<?>> event) {
        ModEntities.register(event);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModModels.setupRenderLayers();
    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event) {
        ModSounds.registerSounds(event);
    }

    @SubscribeEvent
    public static void onModificationRegistry(RegistryEvent.Register<GearModification> event) {
        ModGearModifications.init(event);
    }



    @SubscribeEvent
    public static void registerBlockColorHandles(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, blockDisplayReader, pos, tintIndex) -> {
            if(tintIndex < 0 || tintIndex > 1 || pos == null) {
                return -1;
            }
            return WorldHelper.getBlockEntity(blockDisplayReader, pos, BackpackBlockEntity.class).map(te -> tintIndex == 0 ? te.getBackpackWrapper().getMainColor() : te.getBackpackWrapper().getAccentColor()).orElse(getDefaultColor(tintIndex));
        }, ModBlocks.XL_BACKPACK);
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        event.getItemColors().register((backpack, layer) -> {
            if(layer > 1 || !(backpack.getItem() instanceof BackpackItem)) {
                return -1;
            }
            return backpack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).map(backpackWrapper -> {
                if(layer == 0) {
                    return backpackWrapper.getMainColor();
                }
                else if(layer == 1) {
                    return backpackWrapper.getAccentColor();
                }
                return -1;
            }).orElse(BackpackWrapper.DEFAULT_CLOTH_COLOR);
        }, ModItems.XL_BACKPACK);
    }

    public static int getDefaultColor(int tintIndex) {
        return tintIndex == 0 ? BackpackWrapper.DEFAULT_CLOTH_COLOR : BackpackWrapper.DEFAULT_BORDER_COLOR;
    }

}
