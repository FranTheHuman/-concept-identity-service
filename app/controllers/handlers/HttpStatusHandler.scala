package controllers.handlers

import cats.effect.IO
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import play.api.http.Status.{FORBIDDEN, INTERNAL_SERVER_ERROR, NOT_FOUND, NO_CONTENT, OK, UNAUTHORIZED}
import play.api.libs.ws.WSResponse

import scala.concurrent.{ExecutionContext, Future}

object HttpStatusHandler {

  implicit val log: Logger[IO] =
    Slf4jLogger.getLogger[IO]

  def handleStatus[T](response: WSResponse, result: T)(implicit ec: ExecutionContext): Future[T] =
    response.status match {
      case OK                    => Future.successful(result)
      case NO_CONTENT            => Future.successful(result)
      case FORBIDDEN             => Future.failed(new Throwable(s"Forbidden - ${response.body}"))
      case UNAUTHORIZED          => Future.failed(new Throwable(s"Unauthorized - ${response.body}"))
      case NOT_FOUND             => Future.failed(new Throwable(s"Not Found - ${response.body}"))
      case INTERNAL_SERVER_ERROR => Future.failed(new Throwable(s"Internal Server Error - ${response.body}"))
      case status                => Future.failed(new Throwable(s"Not Match Status Response - $status"))
    }

}
