package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import server.dao.ItemDao;
import server.dao.ItemDaoImpl;
import server.dao.UserDao;
import server.dao.UserDaoImpl;
import server.grpc.ItemServiceImpl;
import server.grpc.UserServiceImpl;

import java.io.IOException;

public class ServerMain
{
  private static final int PORT = 9090;

  public static void main(String[] args) throws IOException, InterruptedException
  {
    ItemDao itemDao = new ItemDaoImpl();
    UserDao userDao = new UserDaoImpl();

    Server server = ServerBuilder.forPort(PORT)
        .addService(new ItemServiceImpl(itemDao))
        .addService(new UserServiceImpl(userDao))
        .build()
        .start();

    System.out.println("boom boom we start");
    System.out.println("on port " + PORT);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("kaboom");
      server.shutdown();
    }));

    server.awaitTermination();
  }
}