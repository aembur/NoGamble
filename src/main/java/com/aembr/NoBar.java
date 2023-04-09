package com.aembr;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
	modid = com.aembr.NoBar.MODID,
	name = com.aembr.NoBar.NAME,
	version = com.aembr.NoBar.VERSION
)
public class NoBar {
	public static final String MODID = "nobar";
	public static final String NAME = "NoBar";
	public static final String VERSION = "0.2";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent preinit) {
		LOGGER.info("Hello, world!");
	}
}
