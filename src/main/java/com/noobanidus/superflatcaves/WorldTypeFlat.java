package com.noobanidus.superflatcaves;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;

public class WorldTypeFlat extends WorldType {
    public WorldTypeFlat(String name) {
        super(name);
    }

    public static WorldTypeFlat createFlatWorld() {
        WorldTypeFlat flat = new WorldTypeFlat("flat");

        WORLD_TYPES[flat.getId()] = null;

        flat.id = 1;
        WorldType.FLAT = flat;
        WORLD_TYPES[1] = flat;
        return flat;
    }

    @Override
    public net.minecraft.world.biome.BiomeProvider getBiomeProvider(World world)
    {
        net.minecraft.world.gen.FlatGeneratorInfo flatgeneratorinfo = net.minecraft.world.gen.FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
        return new net.minecraft.world.biome.BiomeProviderSingle(net.minecraft.world.biome.Biome.getBiome(flatgeneratorinfo.getBiome(), net.minecraft.init.Biomes.DEFAULT));
    }

    @Override
    public net.minecraft.world.gen.IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
        if (this == FLAT)
            return new ChunkGeneratorFlatCaves(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
        return super.getChunkGenerator(world, generatorOptions);
    }
}
