package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection
{
  private static final String URL = "jdbc:postgresql://ep-orange-mountain-b5ku7vi8-pooler.c-7.us-east-2.aws.neon.tech/neondb?sslmode=require";
  private static final String USER = "neondb_owner";
  private static final String PASSWORD = "npg_mGNMLaSDoY31";

  public static Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}