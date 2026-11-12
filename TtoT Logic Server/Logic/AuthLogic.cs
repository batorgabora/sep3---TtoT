using TtoT_Logic_Server.Data;
using TtoT_Logic_Server.Dtos;
using TtoT_Logic_Server.Models;

namespace TtoT_Logic_Server.Logic;

public class AuthLogic : IAuthLogic
{
    private readonly IUserDataClient dataClient;

    public AuthLogic(IUserDataClient dataClient)
    {
        this.dataClient = dataClient;
    }

    public async Task<UserDto?> LoginAsync(LoginRequestDto request)
    {
        if (string.IsNullOrWhiteSpace(request.UserName) || string.IsNullOrEmpty(request.Password))
        {
            return null;
        }

        User? user = await dataClient.GetByUserNameAsync(request.UserName);
        if (user == null || user.Password != request.Password)
        {
            return null;
        }

        return new UserDto
        {
            Id = user.Id,
            UserName = user.UserName
        };
    }
}