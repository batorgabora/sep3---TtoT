package server.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection
{
  private static final Path CONFIG_FILE = Path.of("db.properties");
  private static final Properties CONFIG = loadConfig();

  private static final String URL = setting("db.url");
  private static final String USER = setting("db.user");
  private static final String PASSWORD = setting("db.password");

  public static Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

  private static Properties loadConfig()
  {
    if (!Files.exists(CONFIG_FILE))
    {
      throw new IllegalStateException("Missing " + CONFIG_FILE.toAbsolutePath()
          + " - copy db.properties.example to db.properties and fill in your values");
    }

    Properties properties = new Properties();
    try (InputStream input = Files.newInputStream(CONFIG_FILE))
    {
      properties.load(input);
    }
    catch (IOException e)
    {
      throw new IllegalStateException("Could not read " + CONFIG_FILE.toAbsolutePath(), e);
    }
    return properties;
  }

  private static String setting(String key)
  {
    String value = CONFIG.getProperty(key);
    if (value == null || value.isBlank())
    {
      throw new IllegalStateException("Missing " + key + " in " + CONFIG_FILE.toAbsolutePath());
    }
    return value.trim();
  }
}