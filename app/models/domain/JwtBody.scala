package models.domain

case class JwtBody(iss: String, id: String, iat: Long, exp: Long)