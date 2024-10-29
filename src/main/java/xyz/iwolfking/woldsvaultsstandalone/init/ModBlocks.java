package xyz.iwolfking.woldsvaultsstandalone.init;

import com.mojang.datafixers.types.Type;
import iskallia.vault.VaultMod;
import iskallia.vault.block.CoinPileDecorBlock;
import iskallia.vault.block.render.ScavengerAltarRenderer;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.CoinBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegistryEvent;
import xyz.iwolfking.woldsvaultsstandalone.WoldsVaults;
import xyz.iwolfking.woldsvaultsstandalone.blocks.*;
import xyz.iwolfking.woldsvaultsstandalone.blocks.renderers.DecoLodestoneRenderer;
import xyz.iwolfking.woldsvaultsstandalone.blocks.renderers.DungeonPedestalRenderer;
import xyz.iwolfking.woldsvaultsstandalone.blocks.renderers.SurvivalMobBarrierRenderer;
import xyz.iwolfking.woldsvaultsstandalone.blocks.tiles.*;

import java.util.function.Consumer;

public class ModBlocks {

    public static final Block CHROMATIC_GOLD_BLOCK;
    public static final Block OMEGA_POG_BLOCK;
    public static final Block ECHO_POG_BLOCK;
    public static final Block GEM_POG_BLOCK;
    public static final Block COMPRESSED_VAULT_DIAMOND_BLOCK;
    public static final Block COMPRESSED_VAULT_ESSENCE;
    public static final Block COMPRESSED_VAULT_ESSENCE_2;


    public static final VaultSalvagerBlock VAULT_SALVAGER_BLOCK;
    public static final IskallianLeavesBlock ISKALLIAN_LEAVES_BLOCK;
    public static final HellishSandBlock HELLISH_SAND_BLOCK;
    public static final DungeonPedestalBlock DUNGEON_PEDESTAL_BLOCK;
    public static final DecoScavengerAltarBlock DECO_SCAVENGER_ALTAR_BLOCK;
    public static final DecoObeliskBlock DECO_OBELISK_BLOCK;
    public static final DecoLodestoneBlock DECO_LODESTONE_BLOCK;
    public static final DecoMonolithBlock DECO_MONOLITH_BLOCK;
    public static final SurvivalMobBarrier SURVIVAL_MOB_BARRIER;

    public static final CoinPileDecorBlock VAULT_PALLADIUM_PILE;
    public static final CoinPileDecorBlock VAULT_IRIDIUM_PILE;
    public static BlockItem VAULT_PALLADIUM;
    public static BlockItem VAULT_IRIDIUM;

    public static final BlockEntityType<VaultSalvagerTileEntity> VAULT_SALVAGER_ENTITY;
    public static final BlockEntityType<IskallianLeavesTileEntity> ISKALLIAN_LEAVES_TILE_ENTITY_BLOCK_ENTITY_TYPE;
    public static final BlockEntityType<HellishSandTileEntity> HELLISH_SAND_TILE_ENTITY_BLOCK_ENTITY_TYPE;
    public static final BlockEntityType<DungeonPedestalTileEntity> DUNGEON_PEDESTAL_TILE_ENTITY_BLOCK_ENTITY_TYPE;
    public static final BlockEntityType<DecoScavengerAltarEntity> DECO_SCAVENGER_ALTAR_ENTITY_BLOCK_ENTITY_TYPE;
    public static final BlockEntityType<DecoObeliskTileEntity> DECO_OBELISK_TILE_ENTITY_BLOCK_ENTITY_TYPE;
    public static final BlockEntityType<DecoLodestoneTileEntity> DECO_LODESTONE_TILE_ENTITY_BLOCK_ENTITY_TYPE;
    public static final BlockEntityType<DecoMonolithTileEntity> DECO_MONOLITH_TILE_ENTITY_BLOCK_ENTITY_TYPE;
    public static final BlockEntityType<SurvivalMobBarrierTileEntity> SURVIVAL_MOB_BARRIER_TILE_ENTITY_BLOCK_ENTITY_TYPE;
    //public static final BlockEntityType<BackpackBlockEntity> SOPHISTICATED_BACKPACK;


    //public static final BackpackBlock XL_BACKPACK;

    static {
        VAULT_PALLADIUM_PILE = new CoinPileDecorBlock();
        VAULT_IRIDIUM_PILE = new CoinPileDecorBlock();
        VAULT_PALLADIUM  = new CoinBlockItem(VAULT_PALLADIUM_PILE, new Item.Properties().tab(iskallia.vault.init.ModItems.VAULT_MOD_GROUP));
        VAULT_IRIDIUM  = new CoinBlockItem(VAULT_IRIDIUM_PILE, new Item.Properties().tab(iskallia.vault.init.ModItems.VAULT_MOD_GROUP));
        VAULT_SALVAGER_BLOCK = new VaultSalvagerBlock();
        ISKALLIAN_LEAVES_BLOCK = new IskallianLeavesBlock();
        HELLISH_SAND_BLOCK = new HellishSandBlock();
        DUNGEON_PEDESTAL_BLOCK = new DungeonPedestalBlock();
        DECO_SCAVENGER_ALTAR_BLOCK = new DecoScavengerAltarBlock();
        DECO_OBELISK_BLOCK = (DecoObeliskBlock) new DecoObeliskBlock();
        DECO_LODESTONE_BLOCK = (DecoLodestoneBlock) new DecoLodestoneBlock();
        DECO_MONOLITH_BLOCK = (DecoMonolithBlock) new DecoMonolithBlock();
        SURVIVAL_MOB_BARRIER = (SurvivalMobBarrier) new SurvivalMobBarrier();
        CHROMATIC_GOLD_BLOCK = new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).strength(3.0F, 6.0F));
        OMEGA_POG_BLOCK = new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(9.0F, 60000.0F));
        ECHO_POG_BLOCK = new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(6.0F, 40000.0F));
        GEM_POG_BLOCK = new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(3.0F, 1000.0F));
        COMPRESSED_VAULT_DIAMOND_BLOCK = new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).strength(5.0F, 500.0F));
        COMPRESSED_VAULT_ESSENCE = new Block(BlockBehaviour.Properties.copy(Blocks.SAND).strength(8.0F, 200.0F));
        COMPRESSED_VAULT_ESSENCE_2 = new Block(BlockBehaviour.Properties.copy(Blocks.SAND).strength(10.0F, 200.0F));
        //XL_BACKPACK = new BackpackBlock(12000);
        VAULT_SALVAGER_ENTITY = BlockEntityType.Builder.of(VaultSalvagerTileEntity::new, new Block[]{VAULT_SALVAGER_BLOCK}).build((Type)null);
        ISKALLIAN_LEAVES_TILE_ENTITY_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(IskallianLeavesTileEntity::new, new Block[]{ISKALLIAN_LEAVES_BLOCK}).build((Type)null);
        HELLISH_SAND_TILE_ENTITY_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(HellishSandTileEntity::new, new Block[]{HELLISH_SAND_BLOCK}).build((Type)null);
        DUNGEON_PEDESTAL_TILE_ENTITY_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(DungeonPedestalTileEntity::new, new Block[]{DUNGEON_PEDESTAL_BLOCK}).build((Type)null);
        DECO_SCAVENGER_ALTAR_ENTITY_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(DecoScavengerAltarEntity::new, new Block[]{DECO_SCAVENGER_ALTAR_BLOCK}).build((Type)null);
        DECO_OBELISK_TILE_ENTITY_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(DecoObeliskTileEntity::new, new Block[]{DECO_OBELISK_BLOCK}).build((Type)null);
        DECO_LODESTONE_TILE_ENTITY_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(DecoLodestoneTileEntity::new, new Block[]{DECO_LODESTONE_BLOCK}).build((Type)null);
        DECO_MONOLITH_TILE_ENTITY_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(DecoMonolithTileEntity::new, new Block[]{DECO_MONOLITH_BLOCK}).build((Type)null);
        SURVIVAL_MOB_BARRIER_TILE_ENTITY_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(SurvivalMobBarrierTileEntity::new, new Block[]{SURVIVAL_MOB_BARRIER}).build((Type)null);
        // SOPHISTICATED_BACKPACK = BlockEntityType.Builder.of(BackpackBlockEntity::new, new Block[]{XL_BACKPACK}).build((Type)null);
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        registerBlock(event, VAULT_SALVAGER_BLOCK, WoldsVaults.id("vault_salvager"));
        registerBlock(event, ISKALLIAN_LEAVES_BLOCK, WoldsVaults.id("iskallian_leaves"));
        registerBlock(event, HELLISH_SAND_BLOCK, WoldsVaults.id("hellish_sand"));
        registerBlock(event, DUNGEON_PEDESTAL_BLOCK, WoldsVaults.id("dungeon_pedestal"));
        registerBlock(event, DECO_SCAVENGER_ALTAR_BLOCK, WoldsVaults.id("scavenger_altar"));
        registerBlock(event, DECO_OBELISK_BLOCK, WoldsVaults.id("obelisk"));
        registerBlock(event, DECO_LODESTONE_BLOCK, WoldsVaults.id("lodestone"));
        registerBlock(event, DECO_MONOLITH_BLOCK, WoldsVaults.id("monolith"));
        registerBlock(event, SURVIVAL_MOB_BARRIER, WoldsVaults.id("mob_barrier_red"));
        registerBlock(event, VAULT_PALLADIUM_PILE, WoldsVaults.id("vault_palladium"));
        registerBlock(event, VAULT_IRIDIUM_PILE, WoldsVaults.id("vault_iridium"));
        registerBlock(event, CHROMATIC_GOLD_BLOCK, WoldsVaults.id("chromatic_gold_block"));
        registerBlock(event, OMEGA_POG_BLOCK, WoldsVaults.id("omega_pog_block"));
        registerBlock(event, ECHO_POG_BLOCK, WoldsVaults.id("echo_pog_block"));
        registerBlock(event, GEM_POG_BLOCK, WoldsVaults.id("gem_pog_block"));
        registerBlock(event, COMPRESSED_VAULT_DIAMOND_BLOCK, WoldsVaults.id("compressed_vault_diamond_block"));
        registerBlock(event, COMPRESSED_VAULT_ESSENCE, WoldsVaults.id("compressed_vault_essence"));
        registerBlock(event, COMPRESSED_VAULT_ESSENCE_2, WoldsVaults.id("compressed_vault_essence_2"));
       // registerBlock(event, XL_BACKPACK, WoldsVaults.id("xl_backpack"));

    }
    public static void registerTileEntities(RegistryEvent.Register<BlockEntityType<?>> event) {
        registerTileEntity(event, VAULT_SALVAGER_ENTITY, VaultMod.id("vault_salvager_tile_entity"));
        registerTileEntity(event, ISKALLIAN_LEAVES_TILE_ENTITY_BLOCK_ENTITY_TYPE, WoldsVaults.id("iskallian_leaves_tile_entity"));
        registerTileEntity(event, HELLISH_SAND_TILE_ENTITY_BLOCK_ENTITY_TYPE, WoldsVaults.id("hellish_sand_tile_entity"));
        registerTileEntity(event, DUNGEON_PEDESTAL_TILE_ENTITY_BLOCK_ENTITY_TYPE, WoldsVaults.id("dungeon_pedestal_tile_entity"));
        registerTileEntity(event, DECO_SCAVENGER_ALTAR_ENTITY_BLOCK_ENTITY_TYPE, WoldsVaults.id("scavenger_altar_deco_tile_entity"));
        registerTileEntity(event, DECO_OBELISK_TILE_ENTITY_BLOCK_ENTITY_TYPE, WoldsVaults.id("obelisk_deco_tile_entity"));
        registerTileEntity(event, DECO_LODESTONE_TILE_ENTITY_BLOCK_ENTITY_TYPE, WoldsVaults.id("lodestone_deco_tile_entity"));
        registerTileEntity(event, DECO_MONOLITH_TILE_ENTITY_BLOCK_ENTITY_TYPE, WoldsVaults.id("monolith_deco_tile_entity"));
        registerTileEntity(event, SURVIVAL_MOB_BARRIER_TILE_ENTITY_BLOCK_ENTITY_TYPE, WoldsVaults.id("mob_barrier_entity"));
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        registerBlockItem(event, VAULT_SALVAGER_BLOCK);
        registerBlockItem(event, ISKALLIAN_LEAVES_BLOCK);
        registerBlockItem(event, HELLISH_SAND_BLOCK);
        registerBlockItem(event, DUNGEON_PEDESTAL_BLOCK);
        registerBlockItem(event, DECO_SCAVENGER_ALTAR_BLOCK);
        registerBlockItem(event, DECO_OBELISK_BLOCK);
        registerBlockItem(event, DECO_LODESTONE_BLOCK);
        registerBlockItem(event, DECO_MONOLITH_BLOCK);
        registerBlockItem(event, SURVIVAL_MOB_BARRIER);
        registerBlockItem(event, CHROMATIC_GOLD_BLOCK);
        registerBlockItem(event, OMEGA_POG_BLOCK);
        registerBlockItem(event, ECHO_POG_BLOCK);
        registerBlockItem(event, GEM_POG_BLOCK);
        registerBlockItem(event, COMPRESSED_VAULT_DIAMOND_BLOCK);
        registerBlockItem(event, COMPRESSED_VAULT_ESSENCE);
        registerBlockItem(event, COMPRESSED_VAULT_ESSENCE_2);
        registerBlockItem(event, VAULT_PALLADIUM_PILE, VAULT_PALLADIUM);
        registerBlockItem(event, VAULT_IRIDIUM_PILE, VAULT_IRIDIUM);

    }

    public static void registerTileEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(DECO_SCAVENGER_ALTAR_ENTITY_BLOCK_ENTITY_TYPE, ScavengerAltarRenderer::new);
        event.registerBlockEntityRenderer(SURVIVAL_MOB_BARRIER_TILE_ENTITY_BLOCK_ENTITY_TYPE, SurvivalMobBarrierRenderer::new);
        event.registerBlockEntityRenderer(DUNGEON_PEDESTAL_TILE_ENTITY_BLOCK_ENTITY_TYPE, DungeonPedestalRenderer::new);
        event.registerBlockEntityRenderer(DECO_LODESTONE_TILE_ENTITY_BLOCK_ENTITY_TYPE, DecoLodestoneRenderer::new);
    }



    private static void registerBlock(RegistryEvent.Register<Block> event, Block block, ResourceLocation id) {
        block.setRegistryName(id);
        event.getRegistry().register(block);
    }



    private static <T extends BlockEntity> void registerTileEntity(RegistryEvent.Register<BlockEntityType<?>> event, BlockEntityType<?> type, ResourceLocation id) {
        type.setRegistryName(id);
        event.getRegistry().register(type);
    }

    private static void registerBlockItemWithEffect(RegistryEvent.Register<Item> event, Block block, int maxStackSize, Consumer<Item.Properties> adjustProperties) {
        Item.Properties properties = (new Item.Properties()).tab(iskallia.vault.init.ModItems.VAULT_MOD_GROUP).stacksTo(maxStackSize);
        adjustProperties.accept(properties);
        BlockItem blockItem = new BlockItem(block, properties) {
            public boolean isFoil(ItemStack stack) {
                return true;
            }
        };
        registerBlockItem(event, block, blockItem);
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block) {
        registerBlockItem(event, block, 64);
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block, int maxStackSize) {
        registerBlockItem(event, block, maxStackSize, (properties) -> {
        });
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block, int maxStackSize, Consumer<Item.Properties> adjustProperties) {
        Item.Properties properties = (new Item.Properties()).tab(iskallia.vault.init.ModItems.VAULT_MOD_GROUP).stacksTo(maxStackSize);
        adjustProperties.accept(properties);
        registerBlockItem(event, block, new BlockItem(block, properties));
    }


    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block, BlockItem blockItem) {
        blockItem.setRegistryName(block.getRegistryName());
        event.getRegistry().register(blockItem);
    }

    private static void registerTallBlockItem(RegistryEvent.Register<Item> event, Block block) {
        DoubleHighBlockItem tallBlockItem = new DoubleHighBlockItem(block, (new Item.Properties()).tab(ModItems.VAULT_MOD_GROUP).stacksTo(64));
        tallBlockItem.setRegistryName(block.getRegistryName());
        event.getRegistry().register(tallBlockItem);
    }
}
