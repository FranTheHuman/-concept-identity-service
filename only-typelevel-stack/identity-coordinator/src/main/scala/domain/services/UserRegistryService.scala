package domain.services

import cats.effect.IO
import infrastructure.http.RestClient

object UserRegistryService {

  case class Device(
      deviceId: String,
      initialDeviceDisplayName: String
  )

  case class CreateUser(
      internalUserId: String,
      phoneCountry: String,
      phoneCode: String,
      phoneNumber: String,
      device: Option[Device] = None
  )

  case class UserCreated(
      fullPhoneNumber: String,
      userId: String,
      // should be timestamp
      createdUtc: String
  )

  case class UpdateUser(
      internalUserId: String,
      name: Option[String],
      surname: Option[String],
      nickname: Option[String],
      device: Option[String] = None
  )

  trait UserRegistryService[F[_]] {
    def createUser(command: CreateUser, token: String): F[Unit]
    def updateUser(command: UpdateUser, token: String): F[Unit]
  }

  object Http extends UserRegistryService[IO]:
    import infrastructure.serialization.Json._

    override def createUser(command: CreateUser, token: String): IO[Unit] =
      RestClient.POST[CreateUser]("localhost:9000/UserService/Create", command, Map("Authorization" -> s"Bearer $token"))

    override def updateUser(command: UpdateUser, token: String): IO[Unit] =
      RestClient.POST[UpdateUser]("localhost:9000/UserService/Update", command, Map("Authorization" -> s"Bearer $token"))
}
