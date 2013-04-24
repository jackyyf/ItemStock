package com.jackyyf.ItemStock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;

/**
 * Author: Jack-YYF
 * Created at: 4/20/13 7:33 PM
 * Published under GNU Public License V3(http://www.gnu.org/licenses/gpl-3.0.txt)
 */
public class ItemStock extends JavaPlugin {

	private Config config;
	private TaskRunner taskdaemon;
	private static ItemStock plugin;

	@Override
	public void onEnable() {
		plugin = this;
		try {
			this.config = new Config();
		} catch (FileNotFoundException e) {
			getLogger().severe("Can't load config file! Plugin disabled.");
			e.printStackTrace();
			getPluginLoader().disablePlugin(this);
		}
		getLogger().info("ItemStock Loaded!");
	}

	@Override
	public void onDisable() {
		taskdaemon.terminate();
	}

	public static ItemStock getPlugin() {
		return plugin;
	}

	public Config Config() {
		return this.config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("is")) {
			String oper;
			try {
				oper = args[0];
			} catch (ArrayIndexOutOfBoundsException e) {
				CommandHandler.printHelp(sender);
				return true;
			}
			if(oper.equals("?") || oper.equalsIgnoreCase("help")) {
				CommandHandler.printHelp(sender, args);
				return true;
			}
			if(oper.equalsIgnoreCase("account")) {
			}
		}
		return false;
	}
}
