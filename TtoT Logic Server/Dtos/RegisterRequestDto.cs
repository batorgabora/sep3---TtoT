namespace TtoT_Logic_Server.Dtos;

public class RegisterRequestDto
{
    public string UserName { get; set; } = "";
    public string Password { get; set; } = "";
    public string ConfirmPassword { get; set; } = "";
}
