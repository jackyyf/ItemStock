package com.jackyyf.ItemStock;

import com.jackyyf.ItemStock.utils.Pair;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Jack-YYF
 * Created at: 4/25/13 12:54 PM
 * Published under GNU Public License V3(http://www.gnu.org/licenses/gpl-3.0.txt)
 */
public class Task {

	public final TaskType type;
	public final Map<String, String> args;
	private final String encoding = "utf-8";

	public Task(String serilized) {
		String[] parts = serilized.split("\\?", 2);
		if(parts.length < 2) throw new IllegalArgumentException("Non-valid serialized string!");
		HashMap<String, String> tmp = new HashMap<String, String>();
		String[] kvpairs = parts[1].split("&");
		for(String kvpair : kvpairs) {
			String[] kv = kvpair.split("=", 2);
			if(kv.length < 2) throw new IllegalArgumentException("Non-valid serialized string!");
			String key, value;
			try {
				key = URLDecoder.decode(kv[0], encoding);
				value = URLDecoder.decode(kv[1], encoding);
				tmp.put(key, value);
			} catch (UnsupportedEncodingException e){
				throw new IllegalArgumentException("Non-valid serialized string!");
			}
		}
		try {
			this.type = TaskType.get(URLDecoder.decode(parts[0], encoding));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Non-valid serialized string!");
		}
		this.args = tmp;
	}

	public Task(TaskType type, Map<String, String> args) {
		if(type == TaskType.Unknown) {
			throw new IllegalArgumentException("Unknown Task Type!");
		}
		this.type = type;
		this.args = args;
	}

	public Task(TaskType type, Pair ... args) {
		if(type == TaskType.Unknown){
			throw new IllegalArgumentException("Unknown Task Type!");
		}
		this.type = type;
		this.args = new HashMap<String, String>();
		for(Pair pair : args) {
			this.args.put(pair.key, pair.value);
		}
	}

	@Override
	public String toString() {
		if(this.type == TaskType.Unknown) {
			throw new IllegalStateException("Unknown task type (Non-valid serialized string?)");
		}
		StringBuilder sb;
		try {
			sb = new StringBuilder(URLEncoder.encode(this.type.toString(), encoding));
			sb.append("?");
			for(String key : this.args.keySet()) {
				sb.append(URLEncoder.encode(key, encoding) + "=" + URLEncoder.encode(this.args.get(key), encoding));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		return sb.toString();
	}
}
