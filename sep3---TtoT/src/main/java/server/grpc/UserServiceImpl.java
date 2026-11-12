package server.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import server.dao.UserDao;
import shared.model.User;
import ttot.grpc.GetUserByUsernameRequest;
import ttot.grpc.UserMessage;
import ttot.grpc.UserServiceGrpc;

import java.sql.SQLException;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase
{
  private final UserDao userDao;

  public UserServiceImpl(UserDao userDao)
  {
    this.userDao = userDao;
  }

  @Override
  public void getUserByUsername(GetUserByUsernameRequest request, StreamObserver<UserMessage> responseObserver)
  {
    try
    {
      User user = userDao.getByUsername(request.getUsername());

      if (user == null)
      {
        responseObserver.onError(Status.NOT_FOUND
            .withDescription("no user called " + request.getUsername())
            .asRuntimeException());
        return;
      }

      UserMessage message = UserMessage.newBuilder()
          .setId(user.getId())
          .setUsername(user.getUsername())
          .setPassword(user.getPassword() == null ? "" : user.getPassword())
          .build();

      responseObserver.onNext(message);
      responseObserver.onCompleted();
    }
    catch (SQLException e)
    {
      responseObserver.onError(Status.INTERNAL
          .withDescription("database error: " + e.getMessage())
          .withCause(e)
          .asRuntimeException());
    }
  }
}