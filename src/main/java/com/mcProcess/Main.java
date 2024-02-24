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
import java.io.InputStream;
import java.io.File;

public class Main extends JavaPlugin {

	@Override

	public void onEnable() {
		getLogger().info("mcP is Starting...");
		getLogger().info("""
               MCProcess is a plugin that allows you to create a process that runs in the background using JS!
				        .-.   .-. .---. .----.
				       |  `.'  |/  ___}| {}  }
				       | |\\ /| |\\     }| .--'
				       `-' ` `-' `---' `-'
				""");
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
			getLogger().severe("Error executing Node.js script: " + e.getMessage());
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
    InputStream inputStream = getResource("pipe.yml");
    if (inputStream == null) {
        getLogger().severe("pipe.yml not found in JAR resources!");
        return null;
    }
    File configFile = new File(getDataFolder(), "pipe.yml");
    if (!configFile.exists()) {
        try {
            Files.copy(inputStream, configFile.toPath());
        } catch (IOException e) {
            getLogger().severe("Failed to copy pipe.yml from resources to plugin data folder!");
            e.printStackTrace();
            return null;
        }
    }

    
    return YamlConfiguration.loadConfiguration(configFile);
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
