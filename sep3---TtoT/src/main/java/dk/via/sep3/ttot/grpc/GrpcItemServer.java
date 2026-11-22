package dk.via.sep3.ttot.grpc;

import dk.via.sep3.ttot.model.Item;
import dk.via.sep3.ttot.service.ItemService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;
import ttot.grpc.CreateItemRequest;
import ttot.grpc.GetAllItemsRequest;
import ttot.grpc.ItemList;
import ttot.grpc.ItemMessage;
import ttot.grpc.ItemServiceGrpc;

import java.util.List;

@GrpcService
public class GrpcItemServer extends ItemServiceGrpc.ItemServiceImplBase
{
  private static final Logger LOG = LoggerFactory.getLogger(GrpcItemServer.class.getName());

  private final ItemService itemService;

  public GrpcItemServer(ItemService itemService)
  {
    this.itemService = itemService;
  }

  @Override
  public void getAllItems(GetAllItemsRequest request, StreamObserver<ItemList> responseObserver)
  {
    try
    {
      List<Item> items = itemService.getAll();

      ItemList.Builder listBuilder = ItemList.newBuilder();
      for (Item item : items)
      {
        listBuilder.addItems(ItemMapper.toProto(item));
      }

      responseObserver.onNext(listBuilder.build());
      LOG.info("GetAllItems: sent {} items", items.size());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      // send the real reason to the C# side instead of a vague "Unknown" status
      LOG.error("GetAllItems failed", e);
      responseObserver.onError(Status.INTERNAL
          .withDescription("Items ain't loading go check the whole code base loser: " + e.getMessage())
          .withCause(e)
          .asRuntimeException());
    }
  }

  @Override
  public void createItem(CreateItemRequest request, StreamObserver<ItemMessage> responseObserver)
  {
    try
    {
      Item item = new Item(request.getName(), request.getDescription(), request.getCategoryId(), null,
          request.getLocation(), request.getLatitude(), request.getLongitude(), request.getUploaderId());
      Item saved = itemService.save(item);

      responseObserver.onNext(ItemMapper.toProto(saved));
      LOG.info("CreateItem: created {}", saved);
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      LOG.error("CreateItem failed", e);
      responseObserver.onError(Status.INTERNAL
          .withDescription("could not create item: " + e.getMessage())
          .withCause(e)
          .asRuntimeException());
    }
  }

  private static class ItemMapper
  {
    static ItemMessage toProto(Item item)
    {
      return ItemMessage.newBuilder()
          .setId(item.getId())
          .setName(nullToEmpty(item.getName()))
          .setDescription(nullToEmpty(item.getDescription()))
          .setCategoryId(item.getCategoryId() == null ? 0 : item.getCategoryId())
          .setUploadDate(item.getUploadDate() == null ? "" : item.getUploadDate().toString())
          .setLocation(nullToEmpty(item.getLocation()))
          .setLatitude(item.getLatitude() == null ? 0.0 : item.getLatitude())
          .setLongitude(item.getLongitude() == null ? 0.0 : item.getLongitude())
          .setUploaderId(item.getUploaderId() == null ? 0 : item.getUploaderId())
          .build();
    }

    private static String nullToEmpty(String value)
    {
      return value == null ? "" : value;
    }
  }
}
