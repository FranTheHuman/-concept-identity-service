package application.decoders

import application.requests.UpdateUserRequest
import cats.*
import cats.effect.*
import cats.implicits.*
import domain.AccessToken.{Jwt, JwtBody}
import domain.Phone.*
import infrastructure.serialization.Json
import io.circe.generic.auto.*
import io.circe.syntax.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim, exceptions}

trait EntityDecoders {
  implicit val fullPhoneDecoder: EntityDecoder[IO, FullPhoneNumber]       = jsonOf[IO, FullPhoneNumber]
  implicit val phoneReqDecoder: EntityDecoder[IO, FullPhoneRequest]       = jsonOf[IO, FullPhoneRequest]
  implicit val userUpdateReqDecoder: EntityDecoder[IO, UpdateUserRequest] = jsonOf[IO, UpdateUserRequest]
}
