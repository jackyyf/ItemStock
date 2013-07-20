package com.jackyyf.utils;

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
public class StringUtils {
	public static String join(String glue, String ... parts) {
		if(parts.length == 0) return "";
		StringBuilder sb = new StringBuilder(parts[0]);
		for(int i = 1; i < parts.length; ++ i) {
			sb.append(glue); sb.append(parts[i]);
		}
		return sb.toString();
	}

	public static String join(char glue, String ... parts) {
		if(parts.length == 0) return "";
		StringBuilder sb = new StringBuilder(parts[0]);
		for(int i = 1; i < parts.length; ++ i) {
			sb.append(glue); sb.append(parts[i]);
		}
		return sb.toString();
	}
}
