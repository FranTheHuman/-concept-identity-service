package models.serialization

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import models.domain.{Jwt, JwtBody}
import models.errors.{ErrorResult, InvalidRequestError}
import models.requests._

trait CirceImplicits {

  implicit val jwtBodyDecoder: Decoder[JwtBody] = deriveDecoder[JwtBody]
  implicit val jwtEncoder: Encoder[Jwt]         = deriveEncoder[Jwt]
  implicit val jwtDecoder: Decoder[Jwt]         = deriveDecoder[Jwt]

  implicit val fullPhoneNumberDecoder: Decoder[FullPhoneRequest]    = deriveDecoder[FullPhoneRequest]
  implicit val fullPhoneNumberEncoder: Encoder[FullPhoneRequest]    = deriveEncoder[FullPhoneRequest]
  implicit val loginRequestDecoder: Decoder[LoginRequest]           = deriveDecoder[LoginRequest]
  implicit val idpLoginRequestEncoder: Encoder[AuthLoginRequest]    = deriveEncoder[AuthLoginRequest]
  implicit val authCodeRequestEncoder: Encoder[AuthCodeRequest]     = deriveEncoder[AuthCodeRequest]
  implicit val updateUserRequestDecoder: Decoder[UpdateUserRequest] = deriveDecoder[UpdateUserRequest]

  implicit val invalidRequestErrorEncoder: Encoder[InvalidRequestError] = deriveEncoder[InvalidRequestError]
  implicit val errorResultEncoder: Encoder[ErrorResult] = deriveEncoder[ErrorResult]

}
