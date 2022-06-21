package models.errors

case class JwtDecodingError(msg: String) extends Throwable
