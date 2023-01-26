package rxn.jdbc.common.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import rxn.jdbc.common.util.logger.Console;
import rxn.jdbc.common.util.logger.FileLogger;

public class Database {
  private static final Logger LOGGER;
  private static Database INSTANCE;

  static {
    try {
      LOGGER = FileLogger.getLogger(Database.class.getName());
    } catch (IOException e) {
      Console.error(e.getMessage());
      Console.warning("App will stop now");
      throw new RuntimeException(e);
    }
  }

  private final Properties props = new Properties();
  private final SQLServerDataSource dataSource = new SQLServerDataSource();

  private Database() {
    loadPropertiesFromFile();
    setupDataSourceProperties();
  }

  public static Database getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Database();
    }
    return INSTANCE;
  }

  private Path getPropertyFilePath() {
    return Paths.get("src/main/resources/db.properties").toAbsolutePath();
  }

  private void loadPropertiesFromFile() {
    try (FileInputStream fs = new FileInputStream(getPropertyFilePath().toString())) {
      props.load(fs);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.toString());
      Console.error(e.getMessage());
      Console.warning("App will stop now");
      throw new RuntimeException(e);
    }
  }

  private void setupDataSourceProperties() {
    dataSource.setServerName(props.getProperty("MSSQL_DB_SERVER_NAME"));
    dataSource.setPortNumber(Integer.parseInt(props.getProperty("MSSQL_DB_PORT")));
    dataSource.setUser(props.getProperty("MSSQL_DB_USERNAME"));
    dataSource.setPassword(props.getProperty("MSSQL_DB_PASSWORD"));
  }

  public Connection getConnection(String dbName) {
    try {
      dataSource.setDatabaseName(dbName);
      return dataSource.getConnection();
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, e.toString());
      Console.error(e.getMessage());
      Console.warning("App will stop now");
      throw new RuntimeException(e);
    }
  }

  public Connection getConnection() {
    return this.getConnection("master");
  }
}
