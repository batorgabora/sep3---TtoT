package server;

import server.dao.CategoryDao;
import server.dao.CategoryDaoImpl;
import server.dao.ItemDao;
import server.dao.ItemDaoImpl;
import server.dao.UserDao;
import server.dao.UserDaoImpl;
import shared.model.Category;
import shared.model.Item;
import shared.model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DaoConsoleTest
{
  public static void main(String[] args)
  {
    UserDao userDao = new UserDaoImpl();
    CategoryDao categoryDao = new CategoryDaoImpl();
    ItemDao itemDao = new ItemDaoImpl();

    try
    {
      System.out.println("--- users ---");
      Map<Integer, String> usernames = new HashMap<>();
      for (User user : userDao.getAll())
      {
        System.out.println(user);
        usernames.put(user.getId(), user.getUsername());
      }

      System.out.println("\n--- categories ---");
      Map<Integer, String> categoryNames = new HashMap<>();
      for (Category category : categoryDao.getAll())
      {
        System.out.println(category);
        categoryNames.put(category.getId(), category.getName());
      }

      System.out.println("\n--- items ---");
      for (Item item : itemDao.getAll())
      {
        System.out.printf("#%d %s [%s] by %s, %s (%s, %s)%n",
            item.getId(), item.getName(),
            categoryNames.get(item.getCategoryId()),
            usernames.get(item.getUploaderId()),
            item.getLocation(), item.getLatitude(), item.getLongitude());
      }

      System.out.println("\n--- create / update / delete test ---");
      Item testItem = new Item("test lamp", "created from java", 2, null,
          "horsens, test", 55.861000, 9.850000, 1);

      itemDao.create(testItem);
      System.out.println("created: " + itemDao.getById(testItem.getId()));

      testItem.setDescription("updated from java");
      itemDao.update(testItem);
      System.out.println("updated: " + itemDao.getById(testItem.getId()));

      System.out.println("deleted: " + itemDao.delete(testItem.getId()));
    }
    catch (SQLException e)
    {
      System.out.println("database error: " + e.getMessage());
    }
  }
}