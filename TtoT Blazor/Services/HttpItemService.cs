using System.Net.Http.Json;
using TtoT_Blazor.Models;

namespace TtoT_Blazor.Services;

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
        return items;
    }
}
