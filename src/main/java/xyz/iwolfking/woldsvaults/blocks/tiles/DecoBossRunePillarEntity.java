package xyz.iwolfking.woldsvaults.blocks.tiles;

import iskallia.vault.block.entity.BossRunePillarTileEntity;
import iskallia.vault.world.data.ServerVaults;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.iwolfking.woldsvaults.init.ModBlocks;

public class DecoBossRunePillarEntity extends BossRunePillarTileEntity {

    public DecoBossRunePillarEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.consuming = false;
    }

    public static void tickClient(Level world, BlockPos pos, BlockState state, DecoBossRunePillarEntity tile) {
        BossRunePillarTileEntity.tickClient(world, pos, state, tile);
    }

    public static void tickServer(Level world, BlockPos pos, BlockState state, DecoBossRunePillarEntity tile) {
        if(ServerVaults.get(world).isPresent()) {
            return;
        }
        BossRunePillarTileEntity.tickServer(world, pos, state, tile);
    }
}
