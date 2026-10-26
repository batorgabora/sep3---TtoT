using Microsoft.AspNetCore.Mvc;
using T2LogicServer.Dtos;
using T2LogicServer.Logic;

namespace T2LogicServer.Controllers;

[ApiController]
[Route("[controller]")]
public class ItemsController : ControllerBase
{
    private readonly IItemLogic itemLogic;

    public ItemsController(IItemLogic itemLogic)
    {
        this.itemLogic = itemLogic;
    }

    [HttpGet]
    public async Task<ActionResult<List<ItemDto>>> GetAll()
    {
        List<ItemDto> items = await itemLogic.GetAllAsync();
        return Ok(items);
    }
}
