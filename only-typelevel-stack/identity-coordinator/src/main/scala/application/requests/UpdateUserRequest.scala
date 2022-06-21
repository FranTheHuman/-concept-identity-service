package application.requests

case class UpdateUserRequest(
  name: Option[String],
  surname: Option[String],
  nickname: Option[String],
  device: Option[String] = None
)