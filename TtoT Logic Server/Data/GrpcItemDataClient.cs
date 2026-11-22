using System.Globalization;
using TtoT_Logic_Server.Dtos;
using TtoT_Logic_Server.Grpc;

namespace TtoT_Logic_Server.Data;

public class GrpcItemDataClient : IItemDataClient
{
    private readonly ItemService.ItemServiceClient client;

    public GrpcItemDataClient(ItemService.ItemServiceClient client)
    {
        this.client = client;
    }

    public async Task<List<ItemDto>> GetAllAsync()
    {
        ItemList reply = await client.GetAllItemsAsync(new GetAllItemsRequest());

        return reply.Items.Select(ToDto).ToList();
    }

    public async Task<ItemDto> CreateAsync(CreateItemDto item)
    {
        ItemMessage reply = await client.CreateItemAsync(new CreateItemRequest
        {
            Name = item.Name,
            Description = item.Description ?? "",
            CategoryId = item.CategoryId,
            Location = item.Location ?? "",
            Latitude = (double)(item.Latitude ?? 0),
            Longitude = (double)(item.Longitude ?? 0),
            UploaderId = item.UploaderId
        });

        return ToDto(reply);
    }

    private static ItemDto ToDto(ItemMessage m)
    {
        return new ItemDto
        {
            Id = m.Id,
            Name = m.Name,
            Description = m.Description,
            CategoryId = m.CategoryId,
            UploadDate = DateOnly.Parse(m.UploadDate, CultureInfo.InvariantCulture),
            Location = m.Location,
            Latitude = (decimal)m.Latitude,
            Longitude = (decimal)m.Longitude,
            UploaderId = m.UploaderId
        };
    }
}