package moda.plugin.module.example.storage;

import java.util.UUID;

import moda.plugin.moda.modules.Module;
import moda.plugin.moda.utils.BukkitFuture;
import moda.plugin.moda.utils.storage.FileStorageHandler;
import moda.plugin.moda.utils.storage.ModuleStorageHandler;

public class ExampleFileStorageHandler extends FileStorageHandler implements ExampleStorageHandler {

	public ExampleFileStorageHandler(final Module<? extends ModuleStorageHandler> module) {
		super(module);
	}

	@Override
	public BukkitFuture<Boolean> addBrokenBlocks(final UUID uuid, final int brokenBlocks) {
		return new BukkitFuture<>(() -> {
			if (this.file.contains(uuid.toString())) {
				this.file.set(uuid.toString(), this.file.getInt(uuid.toString()) + brokenBlocks);
			} else {
				this.file.set(uuid.toString(), brokenBlocks);
			}
			return true;
		});
	}

	@Override
	public BukkitFuture<Integer> getBrokenBlocks(final UUID uuid) {
		return new BukkitFuture<>(() -> this.file.getInt(uuid.toString(), 0));
	}

}
