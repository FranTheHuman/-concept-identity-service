package services.interactors.AuthInteractors

import cats.effect.IO
import com.google.inject.Inject
import models.configurations.AuthTokenConfiguration
import models.requests.FullPhoneRequest
import org.typelevel.log4cats.Logger
import repositories.user.UserRepository
import services.{AuthProviderService, JwtService}

import javax.inject.Singleton

@Singleton
class GenerateCode @Inject() (
    authService: AuthProviderService,
    jwtService: JwtService,
    userRepository: UserRepository,
    config: AuthTokenConfiguration
) {

  def interact(phone: FullPhoneRequest)(implicit log: Logger[IO]): IO[Unit] =
    for {
      _ <- authService.generateCode(phone)
      _ <- log.info(s"Code Generated for phone: $phone")
    } yield ()

}
