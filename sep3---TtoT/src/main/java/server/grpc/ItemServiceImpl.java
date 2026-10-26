package server.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import server.dao.ItemDao;
import shared.model.Item;
import ttot.grpc.GetAllItemsRequest;
import ttot.grpc.ItemList;
import ttot.grpc.ItemMessage;
import ttot.grpc.ItemServiceGrpc;

import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl extends ItemServiceGrpc.ItemServiceImplBase
{
  private final ItemDao itemDao;

  public ItemServiceImpl(ItemDao itemDao)
  {
    this.itemDao = itemDao;
  }

  @Override
  public void getAllItems(GetAllItemsRequest request, StreamObserver<ItemList> responseObserver)
  {
    try
    {
      List<Item> items = itemDao.getAll();

      ItemList.Builder listBuilder = ItemList.newBuilder();
      for (Item item : items)
      {
        listBuilder.addItems(toMessage(item));
      }

      responseObserver.onNext(listBuilder.build());
      responseObserver.onCompleted();
    }
    catch (SQLException e)
    {
      responseObserver.onError(Status.INTERNAL
          .withDescription("database error: " + e.getMessage())
          .withCause(e)
          .asRuntimeException());
    }
  }

  private ItemMessage toMessage(Item item)
  {
    return ItemMessage.newBuilder()
        .setId(item.getId())
        .setName(nullToEmpty(item.getName()))
        .setDescription(nullToEmpty(item.getDescription()))
        .setCategoryId(item.getCategoryId())
        .setUploadDate(item.getUploadDate() == null ? "" : item.getUploadDate().toString())
        .setLocation(nullToEmpty(item.getLocation()))
        .setLatitude(item.getLatitude() == null ? 0.0 : item.getLatitude())
        .setLongitude(item.getLongitude() == null ? 0.0 : item.getLongitude())
        .setUploaderId(item.getUploaderId())
        .build();
  }

  private String nullToEmpty(String value)
  {
    return value == null ? "" : value;
  }
}
