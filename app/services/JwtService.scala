package services

import cats.effect.IO
import com.google.inject.{Inject, Singleton}
import io.circe
import io.circe.DecodingFailure
import models.configurations.AuthTokenConfiguration
import models.domain.{Jwt, JwtBody}
import models.errors.{JwtBodyDecodingFailure, JwtDecodingError, TokenMissingError}
import models.serialization.{CirceImplicits, Serialization}
import pdi.jwt.{JwtAlgorithm, JwtCirce}
import play.api.mvc.Headers

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

@Singleton
class JwtService @Inject()(config: AuthTokenConfiguration)(implicit ec: ExecutionContext) extends CirceImplicits with Serialization {

  private val HEADER_NAME: String = "Authorization"
  private val BEARER: String      = "Bearer "
  private val EMPTY: String       = ""

  def decode(jwt: Jwt): IO[JwtBody] =
    IO
      .pure(JwtCirce.decodeJson(jwt.token, config.publicKey, Seq(JwtAlgorithm.RS256)))
      .flatMap {
        case Failure(exception) => IO.raiseError(exception)
        case Success(value)     => toJwtBody(value)
      }

  def toJwt(Jwt: String): IO[Jwt] = {
    io.circe.parser.decode[Jwt](Jwt) match {
      case Right(value) => IO pure value
      case Left(e)      => IO raiseError JwtDecodingError(e.getMessage)
    }
  }

  def extractToken(headers: Headers): IO[Jwt] =
    headers
      .get(HEADER_NAME) match {
        case Some(token) => IO(Jwt(token.replace(BEARER, EMPTY)))
        case None        => IO raiseError TokenMissingError()
      }

  private def toJwtBody(value: circe.Json): IO[JwtBody] =
    IO
      .fromEither(
        value
          .as[JwtBody]
          .left
          .map((e: DecodingFailure) => JwtBodyDecodingFailure(e.getMessage))
      )

}
