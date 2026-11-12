using TtoT_Blazor.Models;

namespace TtoT_Blazor.Services;

public interface IItemService
{
    Task<List<ItemDto>> GetAllAsync();
}
