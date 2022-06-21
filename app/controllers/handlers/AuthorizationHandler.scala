package controllers.handlers

import cats.effect.IO
import com.google.inject.{Inject, Singleton}
import play.api.mvc.Request
import services.JwtService

@Singleton
class AuthorizationHandler @Inject()(jwtService: JwtService) {

  def auth[T](request: Request[T]): IO[String] =
    jwtService
      .extractToken(request.headers)
      .flatMap(jwtService.decode)
      .map(_.id)

}
