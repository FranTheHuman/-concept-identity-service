package controllers

import cats.effect.unsafe.implicits.global
import controllers.handlers.AuthorizationHandler
import controllers.handlers.ErrorHandler.serverErrorHandler
import models.requests.UpdateUserRequest
import models.serialization.RequestDecodable
import play.api.mvc.{Action, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject() (
    val controllerComponents: ControllerComponents,
    authorizer: AuthorizationHandler
)(implicit ec: ExecutionContext)
    extends BaseController
    with RequestDecodable {

  def updateUser(): Action[UpdateUserRequest] =
    Action(decode[UpdateUserRequest]).async { request =>
      authorizer
        .auth(request)
        .map(userId => Ok(userId))
        .handleErrorWith(serverErrorHandler)
        .unsafeToFuture()
    }

  //    (for {
  //      jwt         <- jwtService.extractToken(request.headers)
  //      jwtBody     <- jwtService.decode(jwt, config.publicKey)
  //      userRequest <- IO pure request.body
  //      _           <- userRepository.update(userRequest.copy(id = Some(jwtBody.id)))
  //      response    <- IO pure Ok("User updated successfully")
  //    } yield response).handleErrorWith(serverErrorHandler).unsafeToFuture()

}
