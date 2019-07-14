package com.mineglade.moda.example.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.mineglade.moda.modules.Module;
import com.mineglade.moda.utils.BukkitFuture;
import com.mineglade.moda.utils.storage.DatabaseStorageHandler;

public class ExampleDatabaseStorageHandler extends DatabaseStorageHandler implements ExampleStorageHandler {

	public ExampleDatabaseStorageHandler(final Module<?> module) {
		super(module);
	}

	@Override
	public void setup() throws SQLException {
		this.db.createTableIfNonexistent("moda_blocksbroken", "CREATE TABLE `" + this.db.getDatabase() + "`.`moda_blocksbroken` "
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
