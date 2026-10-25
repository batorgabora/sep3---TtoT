package server.dao;

import server.database.DataBaseConnection;
import shared.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao
{
  @Override
  public List<Category> getAll() throws SQLException
  {
    String sql = "select id, name from ttot.category order by id";
    List<Category> categories = new ArrayList<>();

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery())
    {
      while (resultSet.next())
      {
        categories.add(new Category(resultSet.getInt("id"), resultSet.getString("name")));
      }
    }
    return categories;
  }

  @Override
  public Category getById(int id) throws SQLException
  {
    String sql = "select id, name from ttot.category where id = ?";

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, id);
      try (ResultSet resultSet = statement.executeQuery())
      {
        if (resultSet.next())
        {
          return new Category(resultSet.getInt("id"), resultSet.getString("name"));
        }
        return null;
      }
    }
  }
}