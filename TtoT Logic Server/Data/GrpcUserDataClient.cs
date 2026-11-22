using Grpc.Core;
using TtoT_Logic_Server.Grpc;
using TtoT_Logic_Server.Models;

namespace TtoT_Logic_Server.Data;

public class GrpcUserDataClient : IUserDataClient
{
    private readonly UserService.UserServiceClient client;

    public GrpcUserDataClient(UserService.UserServiceClient client)
    {
        this.client = client;
    }

    public async Task<User?> GetByUserNameAsync(string userName)
    {
        try
        {
            UserMessage reply = await client.GetUserByUsernameAsync(new GetUserByUsernameRequest { Username = userName });

            return new User
            {
                Id = reply.Id,
                UserName = reply.Username,
                Password = reply.Password
            };
        }
        catch (RpcException e) when (e.StatusCode == StatusCode.NotFound)
        {
            return null;
        }
    }

    public async Task<User?> CreateAsync(string userName, string password)
    {
        try
        {
            UserMessage reply = await client.CreateUserAsync(new CreateUserRequest { Username = userName, Password = password });

            return new User
            {
                Id = reply.Id,
                UserName = reply.Username,
                Password = reply.Password
            };
        }
        catch (RpcException e) when (e.StatusCode == StatusCode.AlreadyExists)
        {
            return null;
        }
    }
}