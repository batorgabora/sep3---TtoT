package dk.via.sep3.ttot.grpc;

import dk.via.sep3.ttot.model.User;
import dk.via.sep3.ttot.service.UserService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;
import ttot.grpc.CreateUserRequest;
import ttot.grpc.GetUserByUsernameRequest;
import ttot.grpc.UserMessage;
import ttot.grpc.UserServiceGrpc;

import java.util.Optional;

@GrpcService
public class GrpcUserServer extends UserServiceGrpc.UserServiceImplBase
{
  private static final Logger LOG = LoggerFactory.getLogger(GrpcUserServer.class.getName());

  private final UserService userService;

  public GrpcUserServer(UserService userService)
  {
    this.userService = userService;
  }

  @Override
  public void getUserByUsername(GetUserByUsernameRequest request, StreamObserver<UserMessage> responseObserver)
  {
    try
    {
      Optional<User> user = userService.getByUsername(request.getUsername());

      if (user.isEmpty())
      {
        responseObserver.onError(Status.NOT_FOUND
            .withDescription("no user by that name" + request.getUsername())
            .asRuntimeException());
        return;
      }

      responseObserver.onNext(UserMapper.toProto(user.get()));
      LOG.info("GetUserByUsername: found {}", user.get());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      LOG.error("GetUserByUsername failed", e);
      responseObserver.onError(Status.INTERNAL
          .withDescription("User didn't load,skill issue lol: " + e.getMessage())
          .withCause(e)
          .asRuntimeException());
    }
  }

  @Override
  public void createUser(CreateUserRequest request, StreamObserver<UserMessage> responseObserver)
  {
    try
    {
      Optional<User> user = userService.register(request.getUsername(), request.getPassword());

      if (user.isEmpty())
      {
        responseObserver.onError(Status.ALREADY_EXISTS
            .withDescription("username already taken: " + request.getUsername())
            .asRuntimeException());
        return;
      }

      responseObserver.onNext(UserMapper.toProto(user.get()));
      LOG.info("CreateUser: created {}", user.get());
      responseObserver.onCompleted();
    }
    catch (Exception e)
    {
      LOG.error("CreateUser failed", e);
      responseObserver.onError(Status.INTERNAL
          .withDescription("could not create user: " + e.getMessage())
          .withCause(e)
          .asRuntimeException());
    }
  }

  private static class UserMapper
  {
    static UserMessage toProto(User user)
    {
      return UserMessage.newBuilder()
          .setId(user.getId())
          .setUsername(user.getUsername())
          .setPassword(user.getPassword() == null ? "" : user.getPassword())
          .build();
    }
  }
}
