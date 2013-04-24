package com.jackyyf.ItemStock;

import com.jackyyf.ItemStock.utils.StringUtils;

import java.io.*;
import java.util.HashMap;

/**
 * Author: Jack-YYF
 * Created at: 4/21/13 9:28 PM
 * Published under GNU Public License V3(http://www.gnu.org/licenses/gpl-3.0.txt)
 */
public class Language {

	private final ItemStock plugin;
	private HashMap<String, String> lang;
	private final String notFound = "{$KEY} is not found in language file!";

	public Language (ItemStock plugin) throws IOException {
		this.plugin = plugin;
		this.loadLang(plugin.getConfig().getString("client.lang"));
	}

	private void loadLang(String lang) throws IOException{
		File langFile = new File(this.plugin.getDataFolder().getPath() + File.separator + lang + ".conf");
		if(!langFile.exists()) {
			throw new FileNotFoundException("Can't find " + lang + ".conf in data folder!");
		}
		this.lang = new HashMap<String, String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(langFile));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Can't open " + lang + ".conf in data folder!");
		}
		do {
			String line;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				throw new IOException("IO Error when reading from " + lang + ".conf");
			}
			if(line == null) break;
			if(!line.contains("=")) continue;
			String[] tokens = line.split("=", 2);
			this.lang.put(tokens[0].trim(), tokens[1].trim());
		} while(true);
		reader.close();
	}

	public String get(String node) {
		String result = this.lang.get(node);
		if(result == null) {
			return StringUtils.format(this.notFound, "KEY", node);
		}
		return result;
	}
}
