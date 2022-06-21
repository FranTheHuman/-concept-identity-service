package models.requests

case class FullPhoneRequest(country: String, phoneCode: String, phoneNumber: String) {
  def toAuth(): AuthCodeRequest = AuthCodeRequest(phoneCode + phoneNumber)
}
