package com.aembr;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;

@Mod(
		modid = NoGamble.MODID,
		name = NoGamble.NAME,
		version = NoGamble.VERSION
)
public class NoGamble {
	public static final String MODID = "nogamble";
	public static final String NAME = "NoGamble";
	public static final String VERSION = "0.2";

	private static final String CONFIG_FILE_NAME = "nogamble.json";

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File configFile = new File(event.getModConfigurationDirectory(), CONFIG_FILE_NAME);
		if (!configFile.exists()) {
			try {
				InputStream defaultConfigStream = getClass().getResourceAsStream("/default-config.json");
				Files.copy(defaultConfigStream, configFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
