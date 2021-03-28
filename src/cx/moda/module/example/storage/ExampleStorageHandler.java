package cx.moda.module.example.storage;

import java.util.UUID;

import cx.moda.moda.module.storage.ModuleStorageHandler;

public interface ExampleStorageHandler extends ModuleStorageHandler {

	void addBrokenBlocks(UUID uuid, int brokenBlocks) throws Exception;

	int getBrokenBlocks(UUID uuid) throws Exception;

}
