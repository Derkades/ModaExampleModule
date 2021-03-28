package cx.moda.module.example.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import cx.moda.moda.module.Module;
import cx.moda.moda.module.storage.DatabaseStorageHandler;
import xyz.derkades.derkutils.DatabaseUtils;

public class ExampleDatabaseStorageHandler extends DatabaseStorageHandler implements ExampleStorageHandler {

	public ExampleDatabaseStorageHandler(final Module<?> module) {
		super(module);
	}

	@Override
	public void setup() throws SQLException {
		try (Connection conn = this.getConnection()) {
			DatabaseUtils.createTableIfNonexistent(conn, "moda_blocksbroken",
					"CREATE TABLE `blocksbroken` " +
					"(`uuid` VARCHAR(100) NOT NULL, `blocksbroken` INT() NOT NULL, PRIMARY KEY (`uuid`)) " +
					"ENGINE = InnoDB");
		}
	}

	@Override
	public void addBrokenBlocks(final UUID uuid, final int blocksBroken) throws SQLException {
		try (Connection conn = this.getConnection();
				final PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO moda_blocksbroken (uuid, blocksbroken) VALUES (?, ?) ON DUPLICATE KEY UPDATE blocksbroken=blocksbroken+?")) {
			statement.setString(1, uuid.toString());
			statement.setInt(2, blocksBroken);
			statement.setInt(3, blocksBroken);
			statement.execute();
		}
	}

	@Override
	public int getBrokenBlocks(final UUID uuid) throws SQLException {
		try (Connection conn = this.getConnection();
				final PreparedStatement statement = conn.prepareStatement("SELECT blocksbroken FROM moda_blocksbroken WHERE uuid=?")) {
			statement.setString(1, uuid.toString());
			final ResultSet result = statement.executeQuery();
			return result.next() ? result.getInt(0) : 0;
		}
	}
}
