package server.dao;

import server.database.DataBaseConnection;
import shared.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao
{
  @Override
  public User create(String username) throws SQLException
  {
    String sql = "insert into ttot.\"user\" (username) values (?) returning id";

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery())
      {
        resultSet.next();
        return new User(resultSet.getInt("id"), username);
      }
    }
  }

  @Override
  public List<User> getAll() throws SQLException
  {
    String sql = "select id, username from ttot.\"user\" order by id";
    List<User> users = new ArrayList<>();

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery())
    {
      while (resultSet.next())
      {
        users.add(new User(resultSet.getInt("id"), resultSet.getString("username")));
      }
    }
    return users;
  }

  @Override
  public User getById(int id) throws SQLException
  {
    String sql = "select id, username from ttot.\"user\" where id = ?";

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, id);
      try (ResultSet resultSet = statement.executeQuery())
      {
        if (resultSet.next())
        {
          return new User(resultSet.getInt("id"), resultSet.getString("username"));
        }
        return null;
      }
    }
  }
}