package com.jackyyf.ItemStock.utils;

/**
 * Author: Jack-YYF
 * Created at: 4/21/13 10:29 PM
 * Published under GNU Public License V3(http://www.gnu.org/licenses/gpl-3.0.txt)
 */
public class Pair {
	public final String key;
	public final String value;
	public Pair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static Pair pair(String key, String value) {
		return new Pair(key, value);
	}

	public boolean equal(final Pair compare) {
		return this.key == compare.key && this.value == compare.value;
	}
}
