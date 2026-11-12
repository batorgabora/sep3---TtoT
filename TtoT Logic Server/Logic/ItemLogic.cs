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
}