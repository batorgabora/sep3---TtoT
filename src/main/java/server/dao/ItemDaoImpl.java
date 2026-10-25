package server.dao;

import server.database.DataBaseConnection;
import shared.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao
{
  @Override
  public Item create(Item item) throws SQLException
  {
    if (item.getUploadDate() == null)
    {
      item.setUploadDate(LocalDate.now());
    }

    String sql = "insert into ttot.item (name, description, category_id, upload_date, location, latitude, longitude, uploader_id) "
        + "values (?, ?, ?, ?, ?, ?, ?, ?) returning id";

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
    {
      fillStatement(statement, item);
      try (ResultSet resultSet = statement.executeQuery())
      {
        resultSet.next();
        item.setId(resultSet.getInt("id"));
      }
    }
    return item;
  }

  @Override
  public Item getById(int id) throws SQLException
  {
    String sql = "select * from ttot.item where id = ?";

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, id);
      try (ResultSet resultSet = statement.executeQuery())
      {
        if (resultSet.next())
        {
          return mapItem(resultSet);
        }
        return null;
      }
    }
  }

  @Override
  public List<Item> getAll() throws SQLException
  {
    String sql = "select * from ttot.item order by id";
    List<Item> items = new ArrayList<>();

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery())
    {
      while (resultSet.next())
      {
        items.add(mapItem(resultSet));
      }
    }
    return items;
  }

  @Override
  public boolean update(Item item) throws SQLException
  {
    String sql = "update ttot.item set name = ?, description = ?, category_id = ?, upload_date = ?, "
        + "location = ?, latitude = ?, longitude = ?, uploader_id = ? where id = ?";

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
    {
      fillStatement(statement, item);
      statement.setInt(9, item.getId());
      return statement.executeUpdate() > 0;
    }
  }

  @Override
  public boolean delete(int id) throws SQLException
  {
    String sql = "delete from ttot.item where id = ?";

    try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, id);
      return statement.executeUpdate() > 0;
    }
  }

  private void fillStatement(PreparedStatement statement, Item item) throws SQLException
  {
    statement.setString(1, item.getName());
    statement.setString(2, item.getDescription());
    statement.setInt(3, item.getCategoryId());
    statement.setObject(4, item.getUploadDate());
    statement.setString(5, item.getLocation());
    setNullableDouble(statement, 6, item.getLatitude());
    setNullableDouble(statement, 7, item.getLongitude());
    statement.setInt(8, item.getUploaderId());
  }

  private Item mapItem(ResultSet resultSet) throws SQLException
  {
    return new Item(
        resultSet.getInt("id"),
        resultSet.getString("name"),
        resultSet.getString("description"),
        resultSet.getInt("category_id"),
        resultSet.getObject("upload_date", LocalDate.class),
        resultSet.getString("location"),
        getNullableDouble(resultSet, "latitude"),
        getNullableDouble(resultSet, "longitude"),
        resultSet.getInt("uploader_id"));
  }

  private void setNullableDouble(PreparedStatement statement, int index, Double value) throws SQLException
  {
    if (value == null)
    {
      statement.setNull(index, Types.NUMERIC);
    }
    else
    {
      statement.setDouble(index, value);
    }
  }

  private Double getNullableDouble(ResultSet resultSet, String column) throws SQLException
  {
    double value = resultSet.getDouble(column);
    if (resultSet.wasNull())
    {
      return null;
    }
    return value;
  }
}