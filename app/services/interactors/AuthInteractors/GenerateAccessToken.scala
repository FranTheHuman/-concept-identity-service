package services.interactors.AuthInteractors

import cats.effect.IO
import com.google.inject.Inject
import models.domain.{ Jwt, User }
import models.errors.InteractorDecodingJwtError
import models.requests.LoginRequest
import org.typelevel.log4cats.Logger
import repositories.user.UserRepository
import services.{ AuthProviderService, JwtService }

import javax.inject.Singleton

@Singleton
class GenerateAccessToken @Inject() (
    authService: AuthProviderService,
    jwtService: JwtService,
    userRepository: UserRepository
) {

  def interact(loginRequest: LoginRequest)(implicit log: Logger[IO]): IO[Jwt] =
    for {
      result  <- authService.generateAccessToken(loginRequest)
      jwt     <- jwtService.toJwt(result)
      _       <- log.info(s"Authorization Token Generated for phone: $loginRequest")
      jwtBody <- jwtService.decode(jwt) handleErrorWith decodeErrorHandler
      user = User(id = jwtBody.id)
      id <- userRepository.find(user.id)
      _  <- id.fold(userRepository.persist(user))(_ => IO.unit)
      _  <- log.info(s"User maybe it was created, maybe not.")
    } yield jwt

  val decodeErrorHandler: Throwable => IO[Nothing] =
    (error: Throwable) => IO raiseError InteractorDecodingJwtError(error.getMessage)

}
