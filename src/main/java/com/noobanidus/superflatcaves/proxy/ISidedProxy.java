package com.noobanidus.superflatcaves.proxy;

import net.minecraftforge.fml.common.event.*;

public interface ISidedProxy {
    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void loadComplete(FMLLoadCompleteEvent event);

    void serverStarting(FMLServerStartingEvent event);

    void serverStarted(FMLServerStartedEvent event);
}
