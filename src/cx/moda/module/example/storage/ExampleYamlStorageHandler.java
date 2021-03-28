package cx.moda.module.example.storage;

import java.io.IOException;
import java.util.UUID;

import cx.moda.moda.module.Module;
import cx.moda.moda.module.storage.ModuleStorageHandler;
import cx.moda.moda.module.storage.YamlStorageHandler;

public class ExampleYamlStorageHandler extends YamlStorageHandler implements ExampleStorageHandler {

	public ExampleYamlStorageHandler(final Module<? extends ModuleStorageHandler> module) throws IOException {
		super(module);
	}

	@Override
	public void addBrokenBlocks(final UUID uuid, final int brokenBlocks) {
		if (getYaml().contains(uuid.toString())) {
			getYaml().set(uuid.toString(), getYaml().getInt(uuid.toString()) + brokenBlocks);
		} else {
			getYaml().set(uuid.toString(), brokenBlocks);
		}
	}

	@Override
	public int getBrokenBlocks(final UUID uuid) {
		return getYaml().getInt(uuid.toString(), 0);
	}

}
