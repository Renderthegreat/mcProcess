package com.mcProcess;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends JavaPlugin {

	@Override

	public void onEnable() {
		getLogger().info("mcP is Starting...");

		try {
			ProcessBuilder processBuilder = new ProcessBuilder("node", "../javascript/index.js");
			Process process = processBuilder.start();
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				getLogger().info("Index.js script executed successfully.");
			} else {
				getLogger().warning("Node.js script execution failed with exit code: " + exitCode);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		FileConfiguration config = loadConfig();
		if (config.contains("id")) {
			String id = config.getString("id");
			readFromPipe("mcProcess#" + id);
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("mcP has been disabled.");
	}

	private FileConfiguration loadConfig() {
		Path configFile = Paths.get(getDataFolder().toString(), "mcP/pipe.yml");
		if (!Files.exists(configFile)) {
			saveResource("pipe.yml", false);
		}
		return YamlConfiguration.loadConfiguration(configFile.toFile());
	}

	private void readFromPipe(String pipePath) {
		new Thread(() -> {
			try {
				FileInputStream fis = new FileInputStream(pipePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
				String line;
				while ((line = reader.readLine()) != null) {
					getLogger().info("Received from named pipe: " + line);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
