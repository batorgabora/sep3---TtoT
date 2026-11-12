using TtoT_Logic_Server.Models;

namespace TtoT_Logic_Server.Data;

public interface IUserDataClient
{
    Task<User?> GetByUserNameAsync(string userName);
}