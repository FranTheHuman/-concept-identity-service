package application.errors

import cats.effect.IO
import fs2.text.utf8Encode
import infrastructure.http.HttpError
import org.http4s.{Response, Status}
import pdi.jwt.exceptions.JwtException
import org.slf4j.{Logger, LoggerFactory}

object HttpErrorHandlers {

  def logger: Logger = LoggerFactory.getLogger("HttpErrorHandlers")

  val serverErrorHandler: PartialFunction[Throwable, IO[Response[IO]]] = {
    case error: JwtException =>
      logger.error(error.getMessage)
      IO(
        Response(
          status = Status.InternalServerError,
          body = fs2.Stream("""{"error": "Token decoding error"}""").through(utf8Encode)
        )
      )
    case error: HttpError =>
      logger.error(error.getMessage)
      IO(
        Response(
          status = Status.InternalServerError,
          body = fs2.Stream("""{"error": "AuthProvider error"}""").through(utf8Encode)
        )
      )
    case error: JwtBodyDecodingFailure =>
      logger.error(error.getMessage)
      IO(
        Response(
          status = Status.InternalServerError,
          body = fs2.Stream("""{"error": "Jwt body decoding error"}""").through(utf8Encode)
        )
      )
    case _ : TokenMissingError =>
      logger.error("Missing authorization header")
      IO(
        Response(
          status = Status.Unauthorized,
          body = fs2.Stream("""{"error": "Missing authorization header"}""").through(utf8Encode)
        )
      )
    case error: Throwable =>
      logger.error(error.getMessage)
      IO(Response(status = Status.InternalServerError))
  }

}
