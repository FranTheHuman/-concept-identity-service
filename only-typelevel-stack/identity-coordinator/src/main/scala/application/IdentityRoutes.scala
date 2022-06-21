package application

import application.config.IdentityConfigs
import cats.*
import cats.effect.*
import cats.implicits.*
import domain.Phone.{FullPhoneNumber, PhoneNumber, PhoneRequest}
import domain.services.AuthProviderService.Http
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

class IdentityRoutes(implicit configs: IdentityConfigs) {

  import application.IdentityInteractor.*

  val dsl = Http4sDsl[IO]
  import dsl.*
  
  val BASE_IDENTITY_PATH = "identity"

  def routes: HttpApp[IO] = HttpRoutes.of[IO] {
    case req @ POST -> Root / BASE_IDENTITY_PATH / "code"  =>
      generateCode(req)
    case req @ POST -> Root / BASE_IDENTITY_PATH / "login" =>
      generateAccessToken(req)
    case req @ PUT ->  Root / BASE_IDENTITY_PATH / "user"  =>
      updateUser(req)
  }.orNotFound

}
