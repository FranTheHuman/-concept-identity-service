package models.requests

case class LoginRequest(
    country: String,
    phoneCode: String,
    phoneNumber: String,
    code: String
) {
  override def toString: String = s"$phoneCode$phoneNumber"
  def toAuth(): AuthLoginRequest  = AuthLoginRequest(toString, code)
}
