package com.mineglade.moda.example.storage;

import java.util.UUID;

import com.mineglade.moda.modules.Module;
import com.mineglade.moda.utils.BukkitFuture;
import com.mineglade.moda.utils.storage.FileStorageHandler;
import com.mineglade.moda.utils.storage.ModuleStorageHandler;

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
