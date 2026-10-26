using T2LogicServer.Dtos;

namespace T2LogicServer.Logic;

public interface IItemLogic
{
    Task<List<ItemDto>> GetAllAsync();
}
