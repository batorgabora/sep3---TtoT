using TtoT_Logic_Server.Dtos;

namespace TtoT_Logic_Server.Logic;

public interface IAuthLogic
{
    Task<UserDto?> LoginAsync(LoginRequestDto request);
    Task<UserDto> RegisterAsync(RegisterRequestDto request);
}