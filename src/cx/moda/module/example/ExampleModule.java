package cx.moda.module.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import cx.moda.moda.module.IMessage;
import cx.moda.moda.module.Module;
import cx.moda.moda.module.command.ModuleCommandBuilder;
import cx.moda.moda.module.storage.DatabaseStorageHandler;
import cx.moda.moda.module.storage.FileStorageHandler;
import cx.moda.module.example.storage.ExampleDatabaseStorageHandler;
import cx.moda.module.example.storage.ExampleStorageHandler;
import cx.moda.module.example.storage.ExampleYamlStorageHandler;

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
	public FileStorageHandler getFileStorageHandler() throws IOException {
		// You may choose between writing a YAML or Json storage handler.
		return new ExampleYamlStorageHandler(this);
	}

	@Override
	public void onEnable() {
		this.registerCommand(new ModuleCommandBuilder("blocksbroken")
									.withDescription("View numer of blocks broken")
									.withExecutor(new BlocksBrokenCommand(this))
									.create());

		// You must use the module scheduler, not the bukkit scheduler!
		getScheduler().intervalAsync(5*60*20, 5*60*20, this::save);
	}

	@Override
	public void onDisable() {
		this.save();
	}

	/**
	 * Save stats data from memory to disk
	 */
	private void save() {
		this.getScheduler().async(() -> {
			synchronized(this.BROKEN_BLOCKS) {
				this.BROKEN_BLOCKS.forEach((uuid, amount) -> {
					try {
						getStorage().addBrokenBlocks(uuid, amount);
					} catch (final Exception e) {
						e.printStackTrace();
					}
				});
				this.BROKEN_BLOCKS.clear();
			}
		});
	}

	// The main class is automatically registered as a listener. Don't register it again!
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onBreak(final BlockBreakEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		synchronized(this.BROKEN_BLOCKS) {
			if (this.BROKEN_BLOCKS.containsKey(uuid)) {
				this.BROKEN_BLOCKS.put(uuid, this.BROKEN_BLOCKS.get(uuid) + 1);
			} else {
				this.BROKEN_BLOCKS.put(uuid, 1);
			}
		}
	}

}
