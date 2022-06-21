package domain.services

import cats.effect.IO
import domain.AccessToken.Jwt
import domain.Phone.{FullPhoneNumber, PhoneNumber, PhoneRequest}
import infrastructure.http.RestClient

object AuthProviderService:
  trait AuthProviderService[F[_]] {
    def generateCode(phoneNumber: PhoneNumber): F[Unit]
    def generateAccessToken(phoneRequest: PhoneRequest): F[Jwt]
  }

  object Http extends AuthProviderService[IO] {
    import infrastructure.serialization.Json.*
    override def generateCode(phone: PhoneNumber): IO[Unit] =
      RestClient.POST[PhoneNumber](
        "http://127.0.0.1:1080/login-tickets/phone",
        phone
      )
    override def generateAccessToken(phoneRequest: PhoneRequest): IO[Jwt] =
      RestClient.POST_[PhoneRequest, Jwt](
        "http://127.0.0.1:1080/login/phone",
        phoneRequest
      )
  }