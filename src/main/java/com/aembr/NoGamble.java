package com.aembr;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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
	private File configDir;
	private List<Pattern> patterns;
	private List<String> notifications;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configDir = event.getModConfigurationDirectory();

		loadConfig();
	}

	private void loadConfig() {
		File configFile = new File(configDir, CONFIG_FILE_NAME);
		if (!configFile.exists()) {
			try {
				InputStream defaultConfigStream = getClass().getResourceAsStream("/default.json");
				Files.copy(defaultConfigStream, configFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			JsonElement element = new JsonParser().parse(new FileReader(configFile));
			JsonObject obj = element.getAsJsonObject();

			// Load patterns from config
			JsonArray patternArray = obj.getAsJsonArray("patterns");
			patterns = new ArrayList<>();
			for (JsonElement patternElement : patternArray) {
				String patternString = patternElement.getAsString();
				Pattern pattern = Pattern.compile(patternString);
				patterns.add(pattern);
			}

			// Load notifications from config
			JsonArray notificationArray = obj.getAsJsonArray("notifications");
			notifications = new ArrayList<>();
			for (JsonElement notificationElement : notificationArray) {
				String notificationString = notificationElement.getAsString();
				notifications.add(notificationString);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
}