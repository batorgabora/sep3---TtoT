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

    public async Task<UserDto> RegisterAsync(RegisterRequestDto request)
    {
        if (string.IsNullOrWhiteSpace(request.UserName))
        {
            throw new ArgumentException("username is required");
        }
        if (string.IsNullOrEmpty(request.Password))
        {
            throw new ArgumentException("password is required");
        }
        if (request.Password != request.ConfirmPassword)
        {
            throw new ArgumentException("passwords do not match");
        }

        User? user = await dataClient.CreateAsync(request.UserName.Trim(), request.Password);
        if (user == null)
        {
            throw new InvalidOperationException("username is already taken");
        }

        return new UserDto
        {
            Id = user.Id,
            UserName = user.UserName
        };
    }
}