namespace BlazorApp1.Models;

// One item as T2 sends it in JSON. Same fields as the "item" table.
public class ItemDto
{
    public int Id { get; set; }
    public string Name { get; set; } = "";
    public string? Description { get; set; }
    public int CategoryId { get; set; }
    public DateOnly UploadDate { get; set; }
    public string? Location { get; set; }
    public decimal? Latitude { get; set; }
    public decimal? Longitude { get; set; }
    public int UploaderId { get; set; }
}
