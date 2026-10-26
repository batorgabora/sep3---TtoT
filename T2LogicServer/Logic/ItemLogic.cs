using T2LogicServer.Data;
using T2LogicServer.Dtos;

namespace T2LogicServer.Logic;

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
