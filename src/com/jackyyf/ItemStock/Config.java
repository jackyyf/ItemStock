package com.jackyyf.ItemStock;

import com.jackyyf.ItemStock.utils.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.List;

/**
 * Author: Jack-YYF
 * Created at: 4/20/13 8:55 PM
 * Published under GNU Public License V3(http://www.gnu.org/licenses/gpl-3.0.txt)
 */
public class Config {
	private FileConfiguration config;
	private static ItemStock plugin;
	private final String path;
	private final String fileNotFound = "Config file '{$PATH}' is not exist or readable!";
	private final String fileNotWritable = "Can't write to file '{$PATH}'";
	private final String saveDefaultConfig = "Config file '{$PATH}' does not exist, default file is saved to disk.";

	public Config(String ... fileName) throws FileNotFoundException {
		plugin = ItemStock.getPlugin();
		if(plugin == null) throw new NullPointerException("Config class has no plugin instance!");
		this.path = StringUtils.join(File.separator, fileName);
		File configFile = new File(plugin.getDataFolder() + File.separator + this.path);
		InputStream def = plugin.getResource(this.path);
		if ( ( (!configFile.exists() ) || (!configFile.canRead() ) ) && (!configFile.isDirectory())) {
			if(def == null)
				throw new FileNotFoundException(StringUtils.format(fileNotFound, "PATH", this.path));
			this.config = YamlConfiguration.loadConfiguration(def);
			try {
				if(! plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
				configFile.createNewFile();
				OutputStream config = new FileOutputStream(configFile);
				InputStream defaultConfig = plugin.getResource(this.path);
				byte[] buffer = new byte[1024];
				int read;
				while((read = defaultConfig.read(buffer)) != -1) {
					config.write(buffer, 0, read);
				}
				defaultConfig.close();
				config.close();
				plugin.getLogger().info(StringUtils.format(this.saveDefaultConfig, "PATH", this.path));
				return ;
			} catch (IOException e) {
				plugin.getLogger().severe(StringUtils.format(this.fileNotWritable, "PATH", this.path));
				plugin.getLogger().info(configFile.toString());
				e.printStackTrace();
			}
			return ;
		}
		this.config = YamlConfiguration.loadConfiguration(configFile);
		if (def != null) {
			this.config.setDefaults(YamlConfiguration.loadConfiguration(def));
		}
	}

	public Config() throws FileNotFoundException {
		this("config.yml");
	}

	public void reload() throws FileNotFoundException {
		File configFile = new File(plugin.getDataFolder() + File.separator + this.path);
		InputStream def = plugin.getResource(this.path);
		if ( ( (!configFile.exists() ) || (!configFile.canRead() ) ) && (!configFile.isDirectory())) {
			if(def == null)
				throw new FileNotFoundException(StringUtils.format(fileNotFound, "PATH", this.path));
			this.config = YamlConfiguration.loadConfiguration(def);
			return ;
		}
		this.config = YamlConfiguration.loadConfiguration(configFile);
		if (def != null) {
			this.config.setDefaults(YamlConfiguration.loadConfiguration(def));
		}
	}

	public boolean getBoolean(String path) {
		return this.config.getBoolean(path);
	}

	public int getInt(String path) {
		return this.config.getInt(path);
	}

	public String getString(String path) {
		return this.config.getString(path);
	}

	public List<?> getList(String path) {
		return this.config.getList(path);
	}

	public List<String> getStringList(String path) {
		return this.config.getStringList(path);
	}

	public void set(String path, Object value) {
		this.config.set(path, value);
	}
}
