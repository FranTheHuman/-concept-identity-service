package repositories

import Globals._
import cats.effect.IO
import com.google.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.dbio.Effect.All
import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext

@Singleton
class PostgresRepository @Inject() (
  override val dbConfigProvider: DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  def run[R](action: DBIOAction[R, NoStream, All]): IO[R] =
    db
      .run(action.transactionally)
      .toIo
}
