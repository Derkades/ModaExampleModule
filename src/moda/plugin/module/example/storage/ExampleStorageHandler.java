package moda.plugin.module.example.storage;

import moda.plugin.moda.module.storage.ModuleStorageHandler;
import moda.plugin.moda.util.BukkitFuture;

import java.util.UUID;

public interface ExampleStorageHandler extends ModuleStorageHandler {

	public BukkitFuture<Boolean> addBrokenBlocks(UUID uuid, int brokenBlocks);

	public BukkitFuture<Integer> getBrokenBlocks(UUID uuid);

}
