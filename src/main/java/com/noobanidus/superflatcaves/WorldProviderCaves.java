package com.noobanidus.superflatcaves;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class WorldProviderCaves extends WorldProviderSurface {
    @Override
    public BlockPos getRandomizedSpawnPoint() {
        BlockPos ret = this.world.getSpawnPoint();

        ret = new BlockPos(ret.getX(), 256, ret.getY());
        int air = 0;
        int attempts = 0;
        Chunk chunk = world.getChunk(ret);

        do {
            while (ret.getY() > 0) {
                ret = ret.down();
                IBlockState state = chunk.getBlockState(ret);
                Block block = state.getBlock();
                if (block == Blocks.AIR) {
                    SuperflatCaves.LOG.info(String.format("Air: %d, Block: %s, Y: %d", air, block.getTranslationKey(), ret.getY()));
                    air++;
                } else {
                    SuperflatCaves.LOG.info(String.format("Air: %d, Block: %s, Y: %d", air, block.getTranslationKey(), ret.getY()));
                    if (air >= 2 && block != Blocks.LAVA && block != Blocks.FLOWING_LAVA) {
                        return ret;
                    }
                    SuperflatCaves.LOG.info("Resetting air to 0");
                    air = 0;
                }
            }

            SuperflatCaves.LOG.info(String.format("Failed for position X: %d, Z: %d", ret.getX(), ret.getZ()));

            ret = new BlockPos(ret.getX(), 256, ret.getZ()).add(2, 0, 2);
            attempts++;
        } while (attempts != SuperflatCaves.SuperflatConfig.ATTEMPTS);

        SuperflatCaves.LOG.error(String.format("Unable to find a suitable spawn location near X: %d, Z: %d after %d attempts. Falling back on default.", ret.getX(), ret.getZ(), SuperflatCaves.SuperflatConfig.ATTEMPTS));
        return super.getRandomizedSpawnPoint();
    }
}
