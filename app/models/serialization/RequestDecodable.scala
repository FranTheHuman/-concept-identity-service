package models.serialization

import io.circe.syntax.EncoderOps
import io.circe.{ Decoder, Encoder, Json }
import models.errors.InvalidRequestError
import play.api.Logging
import play.api.libs.circe.Circe
import play.api.mvc.{ BodyParser, Result, Results }

import scala.concurrent.ExecutionContext

/** Decode body request
  */
trait RequestDecodable extends Circe with Logging with Results with CirceImplicits {

  /** Body parser for json applying validations.
    *
    * @param ec
    *   An implicit execution context.
    * @tparam T
    *   A circe decoder.
    * @return
    *   A body parser.
    */
  protected def decode[T: Decoder](implicit ec: ExecutionContext): BodyParser[T] =
    circe.json.validate(decodeJsonValue[T])

  /** decode json value
    * @param json
    *   json
    * @tparam T
    *   type
    * @return
    *   result
    */
  protected def decodeJsonValue[T: Decoder](json: Json): Either[Result, T] =
    implicitly[Decoder[T]]
      .decodeAccumulating(json.hcursor)
      .leftMap { ex =>
        val json = InvalidRequestError(msg = ex.toString())
        logger.error(s"Error decoding request. Invalid body:\n$json")
        BadRequest(json.asJson)
      }
      .toEither

}
