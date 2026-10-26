package server.dao;

import shared.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao
{
  User create(String username) throws SQLException;
  List<User> getAll() throws SQLException;
  User getById(int id) throws SQLException;
}