package controllers.handlers

import cats.effect.IO
import io.jsonwebtoken.JwtException
import models.errors.{ErrorResult, InteractorDecodingJwtError, JwtBodyDecodingFailure, JwtDecodingError, TokenMissingError}
import models.serialization.{CirceImplicits, Serialization}
import org.postgresql.util.PSQLException
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import play.api.mvc.Result
import play.api.mvc.Results.{InternalServerError, Unauthorized}

object ErrorHandler extends Serialization with CirceImplicits {

  implicit val log: Logger[IO] =
    Slf4jLogger.getLogger[IO]

  val serverErrorHandler: Throwable => IO[Result] = {
    case error: JwtException =>
      log.error(error.getMessage)
      IO(InternalServerError(toJsonMsg("Token decoding error")))

    case error: JwtBodyDecodingFailure =>
      log.error(error.getMessage)
      IO(Unauthorized(toJsonMsg("Authorization header decoding error")))

    case error: InteractorDecodingJwtError =>
      log.error(error.msg)
      IO(InternalServerError(toJsonMsg("Interactor GenerateAccessToken - Jwt decoding error")))

    case _: TokenMissingError =>
      log.error("models.errors.TokenMissingError(Missing authorization header)")
      IO(Unauthorized(toJsonMsg("Missing authorization header")))

    case error: JwtDecodingError =>
      log.error(error.getMessage)
      IO(InternalServerError(toJsonMsg("Jwt decoding error")))

    case error: PSQLException =>
      log.error(error.getMessage)
      IO(InternalServerError(toJsonMsg("Database error")))

    case error: Throwable =>
      log.error(error.toString)
      IO(InternalServerError(toJsonMsg(error.getMessage)))
  }

  private def toJsonMsg(msg: String): String =
    encode(ErrorResult(msg)).toString()

}
