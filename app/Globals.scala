import cats.effect.IO

import scala.concurrent.Future

package object Globals {

  implicit class FuturesImplicits[T](future: Future[T]) {
    def toIo: IO[T] =
      IO.fromFuture {
        IO {
          future
        }
      }
  }
}
