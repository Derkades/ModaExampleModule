package moda.plugin.module.example.storage;

import moda.plugin.moda.module.Module;
import moda.plugin.moda.module.storage.DatabaseStorageHandler;
import moda.plugin.moda.util.BukkitFuture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ExampleDatabaseStorageHandler extends DatabaseStorageHandler implements ExampleStorageHandler {

	public ExampleDatabaseStorageHandler(final Module<?> module) {
		super(module);
	}

	@Override
	public void setup() throws SQLException {
		this.db.createTableIfNonexistent("moda_blocksbroken", "CREATE TABLE `blocksbroken` "
				+ "(`uuid` VARCHAR(100) NOT NULL, `blocksbroken` INT() NOT NULL, PRIMARY KEY (`uuid`)) ENGINE = InnoDB");
	}

	@Override
	public BukkitFuture<Boolean> addBrokenBlocks(final UUID uuid, final int blocksBroken) {
		return new BukkitFuture<>(() -> {
			final PreparedStatement statement = this.db.prepareStatement(
					"INSERT INTO moda_blocksbroken (uuid, blocksbroken) VALUES (?, ?) ON DUPLICATE KEY UPDATE blocksbroken=blocksbroken+?",
					uuid, blocksBroken, blocksBroken);
			statement.execute();
			return true;
		});
	}

	@Override
	public BukkitFuture<Integer> getBrokenBlocks(final UUID uuid) {
		return new BukkitFuture<>(() -> {
			final PreparedStatement statement = this.db.prepareStatement("SELECT blocksbroken FROM moda_blocksbroken WHERE uuid=?", uuid);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				return result.getInt(0);
			} else {
				return 0;
			}
		});
	}
}
