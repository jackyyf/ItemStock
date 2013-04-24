package com.jackyyf.ItemStock.utils;

import com.jackyyf.ItemStock.utils.Pair;

import java.util.HashMap;

/**
 * Author: Jack-YYF
 * Created at: 4/21/13 9:55 PM
 * Published under GNU Public License V3(http://www.gnu.org/licenses/gpl-3.0.txt)
 */
public class StringUtils {

	public static String format(String text, String key, String value) {
		return text.replace("{$" + key + "}", value);
	}

	public static String format(String text, HashMap<String, String> replacement) {
		for(String key : replacement.keySet()) {
			text = text.replace("{$" + key + "}", replacement.get(key));
		}
		return text;
	}

	public static String format(String text, String[] keys, String[] values) {
		int len = keys.length < values.length ? keys.length : values.length;
		for(int i = 0; i < len; ++ i) {
			text = text.replace("{$" + keys + "}", values[i]);
		}
		return text;
	}

	public static String format(String text, Pair ... replacements) {
		for(Pair replacement : replacements) {
			text = text.replace("{$" + replacement.key + "}", replacement.value);
		}
		return text;
	}

	public static String join(String glue, String[] parts) {
		if(parts == null || parts.length == 0) return "";
		StringBuilder ret = new StringBuilder(parts[0]);
		for(int i = 1; i < parts.length; ++ i) {
			ret.append(glue).append(parts[i]);
		}
		return ret.toString();
	}
}
