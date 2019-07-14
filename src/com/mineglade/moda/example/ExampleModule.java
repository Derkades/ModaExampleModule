package com.mineglade.moda.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import com.mineglade.moda.example.storage.ExampleDatabaseStorageHandler;
import com.mineglade.moda.example.storage.ExampleFileStorageHandler;
import com.mineglade.moda.example.storage.ExampleStorageHandler;
import com.mineglade.moda.modules.IMessage;
import com.mineglade.moda.modules.Module;
import com.mineglade.moda.utils.BukkitFuture;
import com.mineglade.moda.utils.storage.DatabaseStorageHandler;
import com.mineglade.moda.utils.storage.FileStorageHandler;

public class ExampleModule extends Module<ExampleStorageHandler> {

	private final Map<UUID, Integer> BROKEN_BLOCKS = new HashMap<>();

	@Override
	public String getName() {
		return "Example";
	}

	@Override
	public IMessage[] getMessages() {
		// If you don't want a langfile, return null.
		return ExampleMessage.values();
	}

	@Override
	public DatabaseStorageHandler getDatabaseStorageHandler() {
		// If you don't want to use MySQL, return null.
		return new ExampleDatabaseStorageHandler(this);
	}

	@Override
	public FileStorageHandler getFileStorageHandler() {
		return new ExampleFileStorageHandler(this);
	}

	@Override
	public void onEnable() {
		this.registerCommand(new BlocksBrokenCommand(this.lang, this.storage));

		// You must use the module scheduler, not the bukkit scheduler!
		this.scheduler.intervalAsync(5*60*20, 5*60*20, this::save);
	}

	@Override
	public void onDisable() {
		this.save();
	}

	/**
	 * Save stats data from memory to disk
	 */
	private void save() {
		this.BROKEN_BLOCKS.forEach((uuid, amount) -> {
			final BukkitFuture<Boolean> future = this.storage.addBrokenBlocks(uuid, amount);
			future.onException((e) -> e.printStackTrace());
		});
		this.BROKEN_BLOCKS.clear();
	}

	// The main class is automatically registered as a listener. Don't register it again!
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onBreak(final BlockBreakEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		if (this.BROKEN_BLOCKS.containsKey(uuid)) {
			this.BROKEN_BLOCKS.put(uuid, this.BROKEN_BLOCKS.get(uuid) + 1);
		} else {
			this.BROKEN_BLOCKS.put(uuid, 1);
		}
	}

}
