package cx.moda.module.example.storage;

import moda.plugin.moda.module.Module;
import moda.plugin.moda.module.storage.ModuleStorageHandler;
import moda.plugin.moda.module.storage.YamlStorageHandler;
import moda.plugin.moda.util.BukkitFuture;

import java.io.IOException;
import java.util.UUID;

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
