using TtoT_Logic_Server.Data;
using TtoT_Logic_Server.Dtos;

namespace TtoT_Logic_Server.Logic;

public class ItemLogic : IItemLogic
{
    private readonly IItemDataClient dataClient;

    public ItemLogic(IItemDataClient dataClient)
    {
        this.dataClient = dataClient;
    }

    public async Task<List<ItemDto>> GetAllAsync()
    {
        List<ItemDto> items = await dataClient.GetAllAsync();

        return items.OrderByDescending(item => item.UploadDate).ToList();
    }

    public async Task<ItemDto> CreateAsync(CreateItemDto item)
    {
        if (string.IsNullOrWhiteSpace(item.Name))
        {
            throw new ArgumentException("name is required");
        }
        if (item.UploaderId <= 0)
        {
            throw new ArgumentException("you must be logged in to report an item");
        }
        if (item.CategoryId <= 0)
        {
            throw new ArgumentException("category is required");
        }
        if (item.Latitude is null || item.Latitude < -90 || item.Latitude > 90)
        {
            throw new ArgumentException("latitude must be between -90 and 90");
        }
        if (item.Longitude is null || item.Longitude < -180 || item.Longitude > 180)
        {
            throw new ArgumentException("longitude must be between -180 and 180");
        }

        item.Name = item.Name.Trim();
        return await dataClient.CreateAsync(item);
    }
}