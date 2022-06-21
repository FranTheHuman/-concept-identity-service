package services

import Globals._
import cats.effect.IO
import com.google.inject.{Inject, Singleton}
import controllers.handlers.HttpStatusHandler.handleStatus
import io.circe.syntax._
import models.configurations.AuthProviderConfiguration
import models.requests.{FullPhoneRequest, LoginRequest}
import models.serialization.{CirceImplicits, Writeable}
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext

@Singleton
class AuthProviderService @Inject() (
    ws: WSClient,
    config: AuthProviderConfiguration,
    jwtService: JwtService
)(implicit ec: ExecutionContext)
    extends CirceImplicits
    with Writeable {

  /** This call to a system that create a ticket of login with the phone number. make some validations and send a
    * generated code to the phone
    * @param fullPhone
    *   \- phone that receive that code
    * @return
    */
  def generateCode(fullPhone: FullPhoneRequest): IO[Unit] =
    ws
      .url(config.url + "/login-tickets/phone")
      .post(fullPhone.toAuth().asJson)
      .flatMap(handleStatus(_, ()))
      .toIo

  /** This call to a system that validate the code and generate a jwt token
    * @param loginRequest
    *   \- phone in the ticket created and the code
    * @return
    */
  def generateAccessToken(loginRequest: LoginRequest): IO[String] =
    ws
      .url(config.url + "/login/phone")
      .post(loginRequest.toAuth().asJson)
      .flatMap(r => handleStatus(r, r.body))
      .toIo

}
