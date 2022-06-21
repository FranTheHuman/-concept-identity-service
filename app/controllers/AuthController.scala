package controllers

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import controllers.handlers.ErrorHandler._
import io.circe.syntax.EncoderOps
import models.requests.{ FullPhoneRequest, LoginRequest }
import models.serialization.RequestDecodable
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import play.api.mvc.{ Action, BaseController, ControllerComponents }
import services.interactors.AuthInteractors.{ GenerateAccessToken, GenerateCode }

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class AuthController @Inject() (
    val controllerComponents: ControllerComponents,
    generateAccessToken: GenerateAccessToken,
    GenerateCode: GenerateCode
)(implicit ec: ExecutionContext)
    extends BaseController
    with RequestDecodable {

  implicit val log: Logger[IO] =
    Slf4jLogger.getLogger[IO]

  def generateCode(): Action[FullPhoneRequest] =
    Action.async(decode[FullPhoneRequest]) { request =>
      GenerateCode
        .interact(request.body)
        .map(_ => NoContent)
        .handleErrorWith(serverErrorHandler)
        .unsafeToFuture()
    }

  def generateAccessToken(): Action[LoginRequest] =
    Action.async(decode[LoginRequest]) { request =>
      generateAccessToken
        .interact(request.body)
        .map(r => Ok(r.asJson))
        .handleErrorWith(serverErrorHandler)
        .unsafeToFuture()
    }

}
