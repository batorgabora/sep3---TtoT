using BlazorApp1.Models;

namespace BlazorApp1.Services;

public interface IItemService
{
    Task<List<ItemDto>> GetAllAsync();
}
