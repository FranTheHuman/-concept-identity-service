package models.serialization

import cats.effect.IO
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

/**
 * trait for transform circe.Json to Case class
 */
trait Serialization extends Writeable {

  def decode[T](json: String)(implicit decoder: Decoder[T]): IO[T] =
    IO.fromEither(io.circe.parser.decode[T](json))

  def encode[T](value: T)(implicit decoder: Encoder[T]): Json =
    value.asJson

}
