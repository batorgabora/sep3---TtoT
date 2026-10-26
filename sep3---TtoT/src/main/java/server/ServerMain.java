package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import server.dao.ItemDao;
import server.dao.ItemDaoImpl;
import server.grpc.ItemServiceImpl;

import java.io.IOException;

public class ServerMain
{
  private static final int PORT = 9090;

  public static void main(String[] args) throws IOException, InterruptedException
  {
    ItemDao itemDao = new ItemDaoImpl();

    Server server = ServerBuilder.forPort(PORT)
        .addService(new ItemServiceImpl(itemDao))
        .build()
        .start();

    System.out.println("on port" + PORT);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("kaboom");
      server.shutdown();
    }));

    server.awaitTermination();
  }
}
