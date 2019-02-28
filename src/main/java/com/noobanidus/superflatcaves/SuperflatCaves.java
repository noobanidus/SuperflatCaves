package com.noobanidus.superflatcaves;

import com.noobanidus.superflatcaves.proxy.ISidedProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
@Mod(modid = SuperflatCaves.MODID, name = SuperflatCaves.MODNAME, version = SuperflatCaves.VERSION)
@SuppressWarnings("WeakerAccess")
public class SuperflatCaves {
    public static final String MODID = "superflatcaves";
    public static final String MODNAME = "SuperflatCaves";
    public static final String VERSION = "GRADLE:VERSION";

    public final static Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(modId = MODID, clientSide = "com.noobanidus.superflatcaves.proxy.ClientProxy", serverSide = "com.noobanidus.superflatcaves.proxy.CommonProxy")
    public static ISidedProxy proxy;

    @Mod.Instance(SuperflatCaves.MODID)
    public static SuperflatCaves instance;

    public static List<Class> EventClasses = Arrays.asList();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }

}
