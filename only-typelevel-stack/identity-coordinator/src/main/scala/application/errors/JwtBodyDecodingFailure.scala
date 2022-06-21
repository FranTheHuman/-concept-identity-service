package application.errors

case class JwtBodyDecodingFailure(message: String) extends Throwable