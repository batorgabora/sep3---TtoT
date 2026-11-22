using Microsoft.AspNetCore.Mvc;
using TtoT_Logic_Server.Dtos;
using TtoT_Logic_Server.Logic;

namespace TtoT_Logic_Server.Controllers;

[ApiController]
[Route("[controller]")]
public class AuthController : ControllerBase
{
    private readonly IAuthLogic authLogic;

    public AuthController(IAuthLogic authLogic)
    {
        this.authLogic = authLogic;
    }

    [HttpPost("login")]
    public async Task<ActionResult<UserDto>> Login([FromBody] LoginRequestDto request)
    {
        UserDto? user = await authLogic.LoginAsync(request);
        if (user == null)
        {
            return Unauthorized("invalid username or password");
        }
        return Ok(user);
    }

    [HttpPost("register")]
    public async Task<ActionResult<UserDto>> Register([FromBody] RegisterRequestDto request)
    {
        try
        {
            UserDto user = await authLogic.RegisterAsync(request);
            return Ok(user);
        }
        catch (ArgumentException e)
        {
            return BadRequest(e.Message);
        }
        catch (InvalidOperationException e)
        {
            return Conflict(e.Message);
        }
    }
}