package cx.moda.module.example.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import cx.moda.moda.module.Module;
import cx.moda.moda.module.storage.DatabaseStorageHandler;

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
	public void addBrokenBlocks(final UUID uuid, final int blocksBroken) throws SQLException {
		try (final PreparedStatement statement = this.db.prepareStatement(
				"INSERT INTO moda_blocksbroken (uuid, blocksbroken) VALUES (?, ?) ON DUPLICATE KEY UPDATE blocksbroken=blocksbroken+?",
				uuid, blocksBroken, blocksBroken)) {
			statement.execute();
		}
	}

	@Override
	public int getBrokenBlocks(final UUID uuid) throws SQLException {
		try (final PreparedStatement statement = this.db.prepareStatement("SELECT blocksbroken FROM moda_blocksbroken WHERE uuid=?", uuid)) {
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				return result.getInt(0);
			} else {
				return 0;
			}
		}
	}
}
