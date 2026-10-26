using System.Globalization;
using T2LogicServer.Dtos;
using T2LogicServer.Grpc;

namespace T2LogicServer.Data;

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

        return reply.Items.Select(m => new ItemDto
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
        }).ToList();
    }
}
