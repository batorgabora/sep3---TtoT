using T2LogicServer.Data;
using T2LogicServer.Grpc;
using T2LogicServer.Logic;
using Grpc.Net.Client;

AppContext.SetSwitch("System.Net.Http.SocketsHttpHandler.Http2UnencryptedSupport", true);

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

builder.Services.AddScoped<IItemLogic, ItemLogic>();

builder.Services.AddScoped<IItemDataClient, GrpcItemDataClient>();

builder.Services.AddSingleton(sp =>
{
    GrpcChannel channel = GrpcChannel.ForAddress(builder.Configuration["T3Address"]!);
    return new ItemService.ItemServiceClient(channel);
});

var app = builder.Build();

app.MapControllers();

app.Run();
