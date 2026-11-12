using Microsoft.AspNetCore.Mvc;
using TtoT_Logic_Server.Dtos;
using TtoT_Logic_Server.Logic;

namespace TtoT_Logic_Server.Controllers;

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