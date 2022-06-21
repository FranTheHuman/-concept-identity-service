package domain

object Phone:

  // --- Code Generation flow ---

  case class FullPhoneNumber(country: String, phoneCode: String, phoneNumber: String):
    override def toString: String = s"$phoneCode$phoneNumber"

  case class PhoneNumber(phoneNumber: String)

  // --- Access Token Generation flow ---

  case class FullPhoneRequest(country: String, phoneCode: String, phoneNumber: String, code: String):
    override def toString: String = s"$phoneCode$phoneNumber"

  case class PhoneRequest(phoneNumber: String, code: String)