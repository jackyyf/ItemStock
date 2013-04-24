package com.jackyyf.ItemStock;

import com.jackyyf.ItemStock.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Author: Jack-YYF
 * Created at: 4/25/13 12:53 PM
 * Published under GNU Public License V3(http://www.gnu.org/licenses/gpl-3.0.txt)
 */
public class TaskRunner {
	private LinkedBlockingQueue<Task> queue;
	private final ItemStock plugin;
	private final Thread taskdaemon;
	private boolean running;

	public TaskRunner() {
		this.plugin = ItemStock.getPlugin();
		this.queue = new LinkedBlockingQueue<Task>(this.plugin.Config().getInt("client.queue"));
		this.taskdaemon = new Thread("Task Daemon Thread") {
			@Override
			public void run() {
				while(true) {
					try {
						Task currentTask = queue.take();
					} catch (InterruptedException e) {
						if(!running) {
							plugin.getLogger().info("Task Daemon stopped.");
							return ;
						}
						e.printStackTrace();
					}
				}
			}
		};
		this.taskdaemon.start();
	}

	public void addTask(Task task) {
	}

	private void saveTask() {
		File dumpFile = new File(StringUtils.join(File.separator, new String[]{plugin.getDataFolder().getAbsolutePath
				(), "task_dump.txt"}));
		OutputStream fout;
		try {
			if(!dumpFile.exists()) dumpFile.createNewFile();
			fout = new FileOutputStream(dumpFile);
		} catch (IOException e) {
			plugin.getLogger().warning("Can't create task dump file task_dump.txt. Will dump to console.");
			fout = System.out; // Maybe a little dirty, but it's simple to copy/paste from console.
			try {
				fout.write("\n".getBytes());
			} catch (IOException _) {
			}
		}
		try {
			while(!this.queue.isEmpty()) {
				Task current = this.queue.poll();
				if(current == null) break;
				fout.write((current.toString() + "\n").getBytes());
			}
			fout.close();
		} catch (IOException e) {
			plugin.getLogger().severe("Error while dumping tasks! All tasks will lost!");
			try {
				fout.close();
			} catch (IOException _){
			}
			e.printStackTrace();
			return ;
		}
	}

	public void terminate() {
		this.running = false;
		this.taskdaemon.interrupt();
		try {
			this.taskdaemon.join(1000); // Wait for 1 seconds to terminate.
		} catch (InterruptedException e) {
		}
		this.saveTask();
	}
}
