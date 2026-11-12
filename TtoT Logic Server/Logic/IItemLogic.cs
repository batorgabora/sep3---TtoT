using TtoT_Logic_Server.Dtos;

namespace TtoT_Logic_Server.Logic;

public interface IItemLogic
{
    Task<List<ItemDto>> GetAllAsync();
}