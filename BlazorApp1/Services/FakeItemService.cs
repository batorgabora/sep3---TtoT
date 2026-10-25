using BlazorApp1.Models;

namespace BlazorApp1.Services;

public class FakeItemService : IItemService
{
    public Task<List<ItemDto>> GetAllAsync()
    {
        var items = new List<ItemDto>
        {
            new ItemDto
            {
                Id = 1,
                Name = "rug",
                Description = "faze rug?",
                CategoryId = 1,
                UploadDate = new DateOnly(2026, 10, 1),
                Location = "faze house",
                Latitude = 420,
                Longitude = 666,
                UploaderId = 1
            }
        };
        return Task.FromResult(items);
    }
}
