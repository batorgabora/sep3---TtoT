using System.Net.Http.Json;
using BlazorApp1.Models;

namespace BlazorApp1.Services;

public class HttpItemService : IItemService
{
    private readonly HttpClient client;

    public HttpItemService(HttpClient client)
    {
        this.client = client;
    }

    public async Task<List<ItemDto>> GetAllAsync()
    {
        List<ItemDto>? items = await client.GetFromJsonAsync<List<ItemDto>>("items");
        return items ?? new List<ItemDto>();
    }
}
