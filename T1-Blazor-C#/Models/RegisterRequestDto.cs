namespace TtoT_Blazor.Models;

public class RegisterRequestDto
{
    public string UserName { get; set; } = "";
    public string Password { get; set; } = "";
    public string ConfirmPassword { get; set; } = "";
}
