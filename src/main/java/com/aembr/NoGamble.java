package com.aembr;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
	private static final String NOTIFICATION_PREFIX = "§f[§cNoGamble§f] ";
	private File configDir;
	private List<Pattern> patterns;
	private List<String> notifications;


	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configDir = event.getModConfigurationDirectory();

		loadConfig();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onClientChat(ClientChatEvent event) {
		String message = event.getMessage();

		// Check if the message matches any of the patterns
		for (Pattern pattern : patterns) {
			if (pattern.matcher(message).matches()) {
				// Cancel the event
				event.setCanceled(true);

				// Print a notification message in the chat
				String notification = NOTIFICATION_PREFIX + "§d" + notifications.get(new Random().nextInt(notifications.size()));
				Minecraft.getMinecraft().player.sendMessage(new TextComponentString(notification));
				return;
			}
		}
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
