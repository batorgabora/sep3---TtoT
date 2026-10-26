using T2LogicServer.Dtos;

namespace T2LogicServer.Data;

public interface IItemDataClient
{
    Task<List<ItemDto>> GetAllAsync();
}
