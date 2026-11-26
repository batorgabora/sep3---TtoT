package dk.via.sep3.ttot.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupInfo
{
  private final Environment environment;

  public StartupInfo(Environment environment)
  {
    this.environment = environment;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void printLinks()
  {
    String httpPort = environment.getProperty("local.server.port",
        environment.getProperty("server.port", "8080"));
    String grpcPort = environment.getProperty("spring.grpc.server.port", "9090");

    System.out.println();
    System.out.println("Swagger UI:   http://localhost:" + httpPort + "/swagger-ui/index.html");
    System.out.println("OpenAPI docs: http://localhost:" + httpPort + "/v3/api-docs");
    System.out.println("gRPC server:  localhost:" + grpcPort);
    System.out.println();
  }
}
