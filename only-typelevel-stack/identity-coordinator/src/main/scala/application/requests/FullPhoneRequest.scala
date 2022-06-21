package application.requests

case class FullPhoneRequest(country: String, phoneCode: String, phoneNumber: String, code: String):
  override def toString: String = s"$phoneCode$phoneNumber"
