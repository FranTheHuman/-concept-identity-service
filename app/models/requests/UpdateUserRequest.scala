package models.requests

case class UpdateUserRequest(
 id: Option[String],
 name: Option[String],
 surname: Option[String],
 nickname: Option[String]
 // device: Option[String] = None
)
