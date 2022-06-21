package domain

object AccessToken:
  case class Jwt(token: String)

  case class JwtBody(iss: String, id: String, iat: Long, exp: Long)