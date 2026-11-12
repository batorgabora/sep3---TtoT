using TtoT_Logic_Server.Dtos;

namespace TtoT_Logic_Server.Data;

public interface IItemDataClient
{
    Task<List<ItemDto>> GetAllAsync();
}