package server.dao;

import shared.model.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao
{
  Item create(Item item) throws SQLException;
  Item getById(int id) throws SQLException;
  List<Item> getAll() throws SQLException;
  boolean update(Item item) throws SQLException;
  boolean delete(int id) throws SQLException;
}