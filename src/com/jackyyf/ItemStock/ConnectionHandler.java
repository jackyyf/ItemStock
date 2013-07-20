package com.jackyyf.ItemStock;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
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
public class ConnectionHandler {

	private ConfigHandler config;
	private SocketAddress addr;
	private SocketChannel channel;
	private Selector selector;
	private int retry;
	private int retryLimit;

	public ConnectionHandler() {
		this.retry = 0;
		this.config = ConfigHandler.Config();
		FileConfiguration config = this.config.getConfig();
		String remoteAddr = config.getString("Remote.Address");
		int remotePort = config.getInt("Remote.Port", 2713);
		this.retryLimit = config.getInt("Remote.Retry", 5);
		addr = new InetSocketAddress(remoteAddr, remotePort);
		this.connect();
	}

	private void connect() {
		if(this.channel != null && this.channel.isConnected()) {
			try {
				this.channel.close();
			} catch (IOException e) {
			}
		}
		try {
			this.channel = SocketChannel.open();
			this.selector = Selector.open();
		} catch (IOException e) {
			ItemStock.getPlugin().getLogger().log(Level.WARNING, "Unknown Exception for init Non-Blocking IO!", e);
			throw new RuntimeException("Unable to init Non-Blocking IO!");
		}
		for(;retry < retryLimit; ++ retry) {
			try {
				this.channel.configureBlocking(false).register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
			} catch (IOException e) {
				continue;
			}
			break;
		}
	}
}
