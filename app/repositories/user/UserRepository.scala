package repositories.user

import cats.effect.IO
import com.google.inject.{Inject, Singleton}
import models.domain.User
import repositories.PostgresRepository

import scala.concurrent.ExecutionContext

@Singleton
class UserRepository @Inject() (
    userStatement: UserStatement,
    db: PostgresRepository
)(implicit executionContext: ExecutionContext) {

  def persist(user: User): IO[Unit] =
      db.run(userStatement.insert(user))

  def find(id: String): IO[Option[String]] =
    db.run(userStatement.findUser(id))

  // def update(user: UpdateUserRequest): IO[Unit] =
  //     db.run(userStatement.update(user))

}
