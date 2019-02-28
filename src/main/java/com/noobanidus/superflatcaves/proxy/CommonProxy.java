package com.noobanidus.superflatcaves.proxy;

import com.noobanidus.superflatcaves.SuperflatCaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

import java.util.Arrays;
import java.util.List;

public class CommonProxy implements ISidedProxy {
    private List<Runnable> LOAD_COMPLETE_HOOKS = Arrays.asList();

    public void preInit(FMLPreInitializationEvent event) {
        SuperflatCaves.EventClasses.forEach(MinecraftForge.EVENT_BUS::register);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
        LOAD_COMPLETE_HOOKS.forEach(Runnable::run);
        SuperflatCaves.LOG.info("SuperflatCaves: Load Complete.");
    }

    public void serverStarting(FMLServerStartingEvent event) {
    }

    public void serverStarted(FMLServerStartedEvent event) {
    }
}
