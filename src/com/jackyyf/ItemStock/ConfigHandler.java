package com.jackyyf.ItemStock;

import com.jackyyf.utils.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * ItemStock, a brand new minecraft trading plugin!
 * Copyright (C) 2012-2013 Jack Yu<root@jackyyf.com>
 * <p/>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
public class ConfigHandler {

	private final String fileName;
	private final File file;

	private FileConfiguration config;

	private static ConfigHandler defaultConfig;

	public static ConfigHandler Config() {
		if(defaultConfig == null)
			defaultConfig = new ConfigHandler();
		return defaultConfig;
	}

	public ConfigHandler() {
		this.fileName = null;
		this.file = null;
		this.reloadConfig();
		this.initConfig();
	}

	public ConfigHandler(String ... fileName) {
		this.fileName = StringUtils.join(File.separatorChar, fileName);
		this.file = new File(ItemStock.getPlugin().getDataFolder(), this.fileName);
		this.reloadConfig();
		this.initConfig();
	}

	public void reloadConfig() {
		if(this.fileName == null) {
			this.config = ItemStock.getPlugin().getConfig();
			return ;
		}
		this.config = YamlConfiguration.loadConfiguration(this.file);
		InputStream defConfig = ItemStock.getPlugin().getResource(this.fileName);
		if (defConfig != null) {
			this.config.setDefaults(YamlConfiguration.loadConfiguration(defConfig));
		}
	}

	public FileConfiguration getConfig() {
		return this.config;
	}

	public void saveConfig() {
		if(this.fileName == null) {
			ItemStock.getPlugin().saveConfig();
			return ;
		}
		try {
			this.getConfig().save(this.file);
		} catch (IOException e) {
			ItemStock.getPlugin().getLogger().log(Level.SEVERE, "Unable to save config to " + this.fileName, e);
		}
	}

	// Auto saving default config.

	private void initConfig() {
		if (this.fileName == null) {
			ItemStock.getPlugin().saveDefaultConfig();
			return ;
		}
		if(!this.file.exists()) {
			ItemStock.getPlugin().saveResource(this.fileName, false);
		}
	}
}
