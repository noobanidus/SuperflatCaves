package com.noobanidus.superflatcaves;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WorldProviderCaves extends WorldProviderSurface {
    @Override
    protected void init() {
        super.init();

        // TODO: Make this a config
        this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    @Override
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
        return null;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5f;
        /*if (SuperflatCaves.SuperflatConfig.OVERRIDE_CELESTIAL_ANGLE) {
            return SuperflatCaves.SuperflatConfig.CELESTIAL_ANGLE;
        }

        return super.calculateCelestialAngle(worldTime, partialTicks);*/
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return SuperflatCaves.SuperflatConfig.SKY_COLOURED;
    }

    @Override
    public int getAverageGroundLevel() {
        return SuperflatCaves.SuperflatConfig.AVERAGE_GROUND_LEVEL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean doesXZShowFog(int x, int z) {
        if (SuperflatCaves.SuperflatConfig.OVERRIDE_FOG) {
            return SuperflatCaves.SuperflatConfig.FOG_VALUE;
        }

        return super.doesXZShowFog(x, z);
    }

    @Override
    public boolean isDaytime() {
        return false;
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }
}
