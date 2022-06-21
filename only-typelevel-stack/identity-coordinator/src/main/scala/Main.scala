import application.IdentityRoutes
import application.config.*
import application.errors.HttpErrorHandlers
import cats.effect.{ExitCode, IO, IOApp}
import infrastructure.http.HttpServer.*
import infrastructure.http.ServerConfig
import org.http4s.dsl.Http4sDsl


object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    boostrapServer()

  def boostrapServer(): IO[ExitCode] = {

    implicit val identityConfigs: IdentityConfigs = new IdentityConfigs()

    implicit val serverConf: ServerConfig = ServerConfig(
      host = identityConfigs.SERVER_HOST,
      port = identityConfigs.SERVER_PORT,
      logBody = identityConfigs.SERVER_BODY,
      logHeaders = identityConfigs.SERVER_HEADER
    )
    
    createServer(new IdentityRoutes().routes) { HttpErrorHandlers.serverErrorHandler }
  }


}
