using TtoT_Logic_Server.Data;
using TtoT_Logic_Server.Grpc;
using TtoT_Logic_Server.Logic;
using Grpc.Net.Client;

AppContext.SetSwitch("System.Net.Http.SocketsHttpHandler.Http2UnencryptedSupport", true);

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

builder.Services.AddScoped<IItemLogic, ItemLogic>();
builder.Services.AddScoped<IItemDataClient, GrpcItemDataClient>();

builder.Services.AddScoped<IAuthLogic, AuthLogic>();
builder.Services.AddScoped<IUserDataClient, GrpcUserDataClient>();

builder.Services.AddSingleton(sp => GrpcChannel.ForAddress(builder.Configuration["T3Address"]!));
builder.Services.AddSingleton(sp => new ItemService.ItemServiceClient(sp.GetRequiredService<GrpcChannel>()));
builder.Services.AddSingleton(sp => new UserService.UserServiceClient(sp.GetRequiredService<GrpcChannel>()));

var app = builder.Build();

app.MapControllers();

app.Run();