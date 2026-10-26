namespace BlazorApp1.Models;

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
