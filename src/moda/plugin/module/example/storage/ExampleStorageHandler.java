package moda.plugin.module.example.storage;

import java.util.UUID;

import moda.plugin.moda.utils.BukkitFuture;
import moda.plugin.moda.utils.storage.ModuleStorageHandler;

public interface ExampleStorageHandler extends ModuleStorageHandler {

	public BukkitFuture<Boolean> addBrokenBlocks(UUID uuid, int brokenBlocks);

	public BukkitFuture<Integer> getBrokenBlocks(UUID uuid);

}
