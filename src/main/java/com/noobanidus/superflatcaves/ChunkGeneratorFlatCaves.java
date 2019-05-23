package com.noobanidus.superflatcaves;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.*;

import javax.annotation.Nonnull;
import java.util.Map;

public class ChunkGeneratorFlatCaves extends ChunkGeneratorFlat {
    private World world;
    private MapGenBase caveGenerator = (SuperflatCaves.SuperflatConfig.HIGHER_CAVES) ? new MapGenCaves256() : new MapGenCaves();
    private MapGenBase ravineGenerator = new MapGenRavine();

    private WorldGenLakes waterLakeGenerator = null;
    private WorldGenLakes lavaLakeGenerator = null;

    private final boolean hasDecoration;
    private final boolean hasDungeons;
    private final boolean hasAnimals;
    private final boolean hasIce;
    private final boolean hasCaves;
    private final boolean hasRavines;

    public ChunkGeneratorFlatCaves(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings) {
        super(worldIn, seed, generateStructures, flatGeneratorSettings);

        this.world = worldIn;

        SuperflatCaves.LOG.info(caveGenerator.getClass().toString());

        caveGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(caveGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE);

        SuperflatCaves.LOG.info(caveGenerator.getClass().toString());

        ravineGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(ravineGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE);


        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(flatGeneratorSettings);
        Map<String, Map<String, String>> map = this.flatWorldGenInfo.getWorldFeatures();

        if (generateStructures) {
            if (map.containsKey("village")) {
                Map<String, String> map1 = map.get("village");

                if (!map1.containsKey("size")) {
                    map1.put("size", "1");
                }

                MapGenVillage temp = new MapGenVillage(map1);
                MapGenVillage villageGenerator = (MapGenVillage) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.VILLAGE);
                this.structureGenerators.put("Village", villageGenerator);
            }

            if (map.containsKey("biome_1")) {
                MapGenScatteredFeature temp = new MapGenScatteredFeature(map.get("biome_1"));
                MapGenScatteredFeature scatteredFeatureGenerator = (MapGenScatteredFeature) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.SCATTERED_FEATURE);
                this.structureGenerators.put("Temple", scatteredFeatureGenerator);
            }

            if (map.containsKey("mineshaft")) {
                MapGenMineshaft temp = new MapGenMineshaft(map.get("mineshaft"));
                MapGenMineshaft mineshaftGenerator = (MapGenMineshaft) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.MINESHAFT);
                this.structureGenerators.put("Mineshaft", mineshaftGenerator);
            }

            if (map.containsKey("stronghold")) {
                MapGenStronghold temp = new MapGenStronghold(map.get("stronghold"));
                MapGenStronghold strongholdGenerator = (MapGenStronghold) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.STRONGHOLD);
                this.structureGenerators.put("Stronghold", strongholdGenerator);
            }

            if (map.containsKey("oceanmonument")) {
                StructureOceanMonument temp = new StructureOceanMonument(map.get("oceanmonument"));
                StructureOceanMonument oceanMonumentGenerator = (StructureOceanMonument) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(temp, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.OCEAN_MONUMENT);
                this.structureGenerators.put("Monument", oceanMonumentGenerator);
            }
        }

        if (map.containsKey("lake")) waterLakeGenerator = new WorldGenLakes(Blocks.WATER);
        if (map.containsKey("lava_lake")) lavaLakeGenerator = new WorldGenLakes(Blocks.LAVA);
        this.hasCaves = map.containsKey("caves");
        this.hasRavines = map.containsKey("ravines");
        this.hasDungeons = map.containsKey("dungeon");
        this.hasDecoration = map.containsKey("decoration");
        this.hasAnimals = map.containsKey("animals");
        this.hasIce = map.containsKey("ice");
    }

    @Override
    @Nonnull
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        for (int i = 0; i < this.cachedBlockIDs.length; ++i) {
            IBlockState iblockstate = this.cachedBlockIDs[i];

            if (iblockstate != null) {
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        chunkprimer.setBlockState(j, i, k, iblockstate);
                    }
                }
            }
        }

        if (hasCaves) {
            caveGenerator.generate(this.world, x, z, chunkprimer);
        }

        if (hasRavines) {
            ravineGenerator.generate(this.world, x, z, chunkprimer);
        }

        for (MapGenBase mapgenbase : this.structureGenerators.values()) {
            mapgenbase.generate(this.world, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int l = 0; l < abyte.length; ++l) {
            abyte[l] = (byte) Biome.getIdForBiome(abiome[l]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        net.minecraft.block.BlockFalling.fallInstantly = true;
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(new BlockPos(i + 16, 0, j + 16));
        boolean flag = false;
        this.random.setSeed(this.world.getSeed());
        long k = this.random.nextLong() / 2L * 2L + 1L;
        long l = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long) x * k + (long) z * l ^ this.world.getSeed());
        ChunkPos chunkpos = new ChunkPos(x, z);

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.random, x, z, flag);

        for (MapGenStructure mapgenstructure : this.structureGenerators.values()) {
            boolean flag1 = mapgenstructure.generateStructure(this.world, this.random, chunkpos);

            if (mapgenstructure instanceof MapGenVillage) {
                flag |= flag1;
            }
        }

        if (this.waterLakeGenerator != null && !flag && this.random.nextInt(4) == 0) {
            if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE)) {
                this.waterLakeGenerator.generate(this.world, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
            }
        }

        if (this.lavaLakeGenerator != null && !flag && this.random.nextInt(8) == 0) {
            if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA)) {
                BlockPos blockpos1 = blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(this.random.nextInt(248) + 8), this.random.nextInt(16) + 8);

                if (blockpos1.getY() < this.world.getSeaLevel() || this.random.nextInt(10) == 0) {
                    this.lavaLakeGenerator.generate(this.world, this.random, blockpos1);
                }
            }
        }

        if (this.hasDungeons) {
            if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON)) {
                for (int i1 = 0; i1 < 8; ++i1) {
                    (new WorldGenDungeons()).generate(this.world, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
                }
            }
        }

        if (this.hasDecoration) {
            biome.decorate(this.world, this.random, blockpos);
        }

        if (this.hasAnimals) {
            if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS))
                WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.random);
        }

        if (this.hasIce) {
            net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE);
        }

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.random, x, z, flag);
        net.minecraft.block.BlockFalling.fallInstantly = false;
    }
}
