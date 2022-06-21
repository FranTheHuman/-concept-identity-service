package application.decoders

import cats.effect.IO
import domain.AccessToken.{Jwt, JwtBody}
import domain.Phone.FullPhoneNumber
import infrastructure.serialization.Json
import io.circe
import io.circe.*
import io.circe.generic.semiauto.*
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim, exceptions}

trait Decoders {
  implicit val jwtBodyDecoder: Decoder[JwtBody] = deriveDecoder[JwtBody]
}
