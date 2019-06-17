package com.noobanidus.superflatcaves;

import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderCaves extends WorldProviderSurface {
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        if (SuperflatCaves.SuperflatConfig.OVERRIDE_CELESTIAL_ANGLE) {
            return SuperflatCaves.SuperflatConfig.CELESTIAL_ANGLE;
        }

        return super.calculateCelestialAngle(worldTime, partialTicks);
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
}
