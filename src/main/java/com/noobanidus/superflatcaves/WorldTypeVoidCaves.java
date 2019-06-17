package com.noobanidus.superflatcaves;

import com.bartz24.voidislandcontrol.config.ConfigOptions;
import com.bartz24.voidislandcontrol.world.WorldTypeVoid;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class WorldTypeVoidCaves extends WorldTypeVoid {
    public WorldTypeVoidCaves() {
        super();

        int myId = getId();
        int oldId = -1;

        for (WorldType worldType : WORLD_TYPES) {
            if (worldType.getName().equals("voidworld") && worldType.getId() != myId) {
                oldId = worldType.getId();
                break;
            }
        }

        if (oldId == -1) {
            // bugger
        } else {
            WORLD_TYPES[myId] = null;
            WORLD_TYPES[oldId] = this;
        }
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
        WorldType override = ObfuscationReflectionHelper.getPrivateValue(WorldTypeVoid.class, this, "overridenWorldType");
        if (override != null)
            return override.getChunkGenerator(world, generatorOptions);

        if (ConfigOptions.worldGenSettings.worldGenType != ConfigOptions.WorldGenSettings.WorldGenType.OVERWORLD && ConfigOptions.worldGenSettings.worldGenType != ConfigOptions.WorldGenSettings.WorldGenType.CUSTOMIZED) {
            String genSettings = "3;1*minecraft:air";
            if (ConfigOptions.worldGenSettings.worldGenType == ConfigOptions.WorldGenSettings.WorldGenType.SUPERFLAT)
                genSettings = ConfigOptions.worldGenSettings.worldGenSpecialParameters;
            return new ChunkGeneratorFlatCaves(world, world.getSeed(), false, genSettings);
        } else
            return new ChunkGeneratorOverworld(world, world.getSeed(), true, generatorOptions);
    }
}
