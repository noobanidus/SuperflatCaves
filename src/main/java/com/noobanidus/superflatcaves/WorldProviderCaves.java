package com.noobanidus.superflatcaves;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class WorldProviderCaves extends WorldProviderSurface {
    @Override
    public BlockPos getRandomizedSpawnPoint() {
        BlockPos ret = this.world.getSpawnPoint();

        int spawnFuzz = 10;
        int spawnFuzzHalf = 5;
        ret = new BlockPos(ret.getX(), 256, ret.getY());
        int air = 0;
        int attempts = 0;
        Chunk chunk = world.getChunk(ret);

        List<Block> spawnSafe = SuperflatCaves.SuperflatConfig.safeSpawnBlocks();

        do {
            while (ret.getY() > 0) {
                ret = ret.down();
                IBlockState state = chunk.getBlockState(ret);
                Block block = state.getBlock();
                if (block.isAir(state, world, ret)) {
                    air++;
                } else {
                    if (air >= 2) {
                        if (spawnSafe.contains(block)) return ret;
                    }
                    air = 0;
                }
            }

            ret = new BlockPos(ret.getX(), 256, ret.getZ()).add(spawnFuzzHalf - world.rand.nextInt(spawnFuzz), 0, spawnFuzzHalf - world.rand.nextInt(spawnFuzz));
            attempts++;
        } while (attempts != 15);

        SuperflatCaves.LOG.error(String.format("Unable to find a suitable spawn location near X: %d, Z: %d after 15 attempts. Falling back on default.", ret.getX(), ret.getZ()));
        return super.getRandomizedSpawnPoint();
    }
}
