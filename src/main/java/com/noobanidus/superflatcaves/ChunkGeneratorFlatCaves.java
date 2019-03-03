package com.noobanidus.superflatcaves;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;

public class ChunkGeneratorFlatCaves extends ChunkGeneratorFlat {
    private World world;
    private MapGenCaves CAVES;

    public ChunkGeneratorFlatCaves(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings) {
        super(worldIn, seed, generateStructures, flatGeneratorSettings);

        this.world = worldIn;
        this.CAVES = new MapGenCaves();
    }

    @Override
    public Chunk generateChunk(int x, int z)
    {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        for (int i = 0; i < this.cachedBlockIDs.length; ++i)
        {
            IBlockState iblockstate = this.cachedBlockIDs[i];

            if (iblockstate != null)
            {
                for (int j = 0; j < 16; ++j)
                {
                    for (int k = 0; k < 16; ++k)
                    {
                        chunkprimer.setBlockState(j, i, k, iblockstate);
                    }
                }
            }
        }

        CAVES.generate(this.world, x, z, chunkprimer);

        for (MapGenBase mapgenbase : this.structureGenerators.values())
        {
            mapgenbase.generate(this.world, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        Biome[] abiome = this.world.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int l = 0; l < abyte.length; ++l)
        {
            abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

}
