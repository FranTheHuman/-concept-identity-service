package application

import application.requests.UpdateUserRequest
import config.IdentityConfigs
import decoders.EntityDecoders
import cats.*
import cats.effect.*
import cats.implicits.*
import domain.AccessToken.Jwt
import domain.Phone.*
import domain.services.AuthProviderService.Http as authProviderService
import domain.services.UserRegistryService.{CreateUser, UpdateUser, Http as userRegistryService}
import io.circe.generic.auto.*
import io.circe.syntax.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.headers.*
import org.http4s.implicits.*
import org.http4s.server.*
import org.typelevel.ci.CIString
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object IdentityInteractor extends EntityDecoders {

  implicit val logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  val dsl = Http4sDsl[IO]
  import dsl.*

  def generateCode(request: Request[IO])(implicit configs: IdentityConfigs): IO[Response[IO]] =
    for {
      phone    <- request.as[FullPhoneNumber]
      _        <- authProviderService.generateCode(PhoneNumber(phone.toString))
      _        <- logger.info(s"Code Generated for phone: $phone")
      response <- Ok("")
    } yield response

  def generateAccessToken(request: Request[IO])(implicit configs: IdentityConfigs): IO[Response[IO]] =
    for {
      phone   <- request.as[FullPhoneRequest]
      jwt     <- authProviderService.generateAccessToken(PhoneRequest(phone.toString, phone.code))
      _       <- logger.info(s"Authorization Token Generated for phone: $phone")
      jwtBody <- JwtInteractor.decode(jwt, configs.PUBLIC_KEY)
      createCommand = CreateUser(jwtBody.id, phone.country, phone.code, phone.phoneNumber)
      _        <- userRegistryService.createUser(createCommand, jwt.token)
      _        <- logger.info(s"User Created with command $createCommand")
      response <- Ok(jwt.asJson)
    } yield response

  def updateUser(request: Request[IO])(implicit configs: IdentityConfigs): IO[Response[IO]]  =
    for {
      user     <- request.as[UpdateUserRequest]
      jwt      <- JwtInteractor.extractToken(request)
      jwtBody  <- JwtInteractor.decode(jwt, configs.PUBLIC_KEY)
      updateCommand = UpdateUser(jwtBody.id, user.name, user.surname, user.nickname, user.device)
      _        <- userRegistryService.updateUser(updateCommand, jwt.token)
      _        <- logger.info(s"User Updated with command: ${updateCommand}")
      response <- Ok("User Updated")
    } yield response

}
