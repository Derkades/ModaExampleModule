package com.mineglade.moda.example.storage;

import java.util.UUID;

import com.mineglade.moda.utils.BukkitFuture;
import com.mineglade.moda.utils.storage.ModuleStorageHandler;

public interface ExampleStorageHandler extends ModuleStorageHandler {

	public BukkitFuture<Boolean> addBrokenBlocks(UUID uuid, int brokenBlocks);

	public BukkitFuture<Integer> getBrokenBlocks(UUID uuid);

}
