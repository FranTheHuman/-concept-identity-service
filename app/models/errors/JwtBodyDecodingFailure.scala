package models.errors

case class JwtBodyDecodingFailure(message: String) extends Throwable
