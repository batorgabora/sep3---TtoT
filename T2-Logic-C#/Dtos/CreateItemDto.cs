namespace TtoT_Logic_Server.Dtos;

public class CreateItemDto
{
    public string Name { get; set; } = "";
    public string? Description { get; set; }
    public int CategoryId { get; set; }
    public string? Location { get; set; }
    public decimal? Latitude { get; set; }
    public decimal? Longitude { get; set; }
    public int UploaderId { get; set; }
}
