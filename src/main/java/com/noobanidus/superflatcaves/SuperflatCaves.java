package com.noobanidus.superflatcaves;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = SuperflatCaves.MODID, name = SuperflatCaves.MODNAME, version = SuperflatCaves.VERSION, dependencies = SuperflatCaves.DEPENDS)
@SuppressWarnings("WeakerAccess")
public class SuperflatCaves {
    public static final String MODID = "superflatcaves";
    public static final String MODNAME = "SuperflatCaves";
    public static final String VERSION = "GRADLE:VERSION";
    public static final String DEPENDS = "after:voidislandcontrol;";

    public final static Logger LOG = LogManager.getLogger(MODID);

    @Mod.Instance(SuperflatCaves.MODID)
    public static SuperflatCaves instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOG.info("Replacing FLAT world generator with delightful, cave-filled generator!");
        WorldTypeFlat.createFlatWorld();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        if (Loader.isModLoaded("voidislandcontrol")) {
            LOG.info("Allowing Void Island Control into the tendril-like folds of caves...");
            Handler.replaceVoid();
        }
    }

}
