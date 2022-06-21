package application

import cats.effect.IO
import domain.AccessToken.{Jwt, JwtBody}
import io.circe
import pdi.jwt.{JwtAlgorithm, JwtCirce}
import errors.TokenMissingError
import scala.util.{Failure, Success}
import decoders.Decoders
import org.http4s.Request
import org.typelevel.ci.CIString

object JwtInteractor extends Decoders {

  def decode(jwt: Jwt, key: String): IO[JwtBody] =
    IO {
      JwtCirce.decodeJson(jwt.token, key, Seq(JwtAlgorithm.RS256))
    }.flatMap {
      case Failure(exception) => IO.raiseError(exception)
      case Success(value) => toJwtBody(value)
    }

  def extractToken(request: Request[IO]): IO[Jwt] =
    request
      .headers
      .get(CIString("Authorization"))
      .fold(IO raiseError TokenMissingError())(ls => IO(Jwt(ls.head.value.replace("Bearer ", ""))))


  private def toJwtBody(value: circe.Json): IO[JwtBody] =
    IO
      .fromEither(
        value
          .as[JwtBody]
          .left
          .map(e => errors.JwtBodyDecodingFailure(e.getMessage))
      )

}
