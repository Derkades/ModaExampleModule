package moda.plugin.module.example.storage;

import java.io.IOException;
import java.util.UUID;

import moda.plugin.moda.modules.Module;
import moda.plugin.moda.utils.BukkitFuture;
import moda.plugin.moda.utils.storage.ModuleStorageHandler;
import moda.plugin.moda.utils.storage.YamlStorageHandler;

public class ExampleYamlStorageHandler extends YamlStorageHandler implements ExampleStorageHandler {

	public ExampleYamlStorageHandler(final Module<? extends ModuleStorageHandler> module) throws IOException {
		super(module);
	}

	@Override
	public BukkitFuture<Boolean> addBrokenBlocks(final UUID uuid, final int brokenBlocks) {
		return new BukkitFuture<>(() -> {
			if (getYaml().contains(uuid.toString())) {
				getYaml().set(uuid.toString(), getYaml().getInt(uuid.toString()) + brokenBlocks);
			} else {
				getYaml().set(uuid.toString(), brokenBlocks);
			}
			return true;
		});
	}

	@Override
	public BukkitFuture<Integer> getBrokenBlocks(final UUID uuid) {
		return new BukkitFuture<>(() -> getYaml().getInt(uuid.toString(), 0));
	}

}
