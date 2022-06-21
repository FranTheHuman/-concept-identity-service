package repositories.user

import com.google.inject.{Inject, Singleton}
import models.domain.User
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext

@Singleton
class UserStatement @Inject() (implicit ec: ExecutionContext) {

  def insert(newUser: User): DBIO[Unit] =
    (UserTable.table += newUser).map(_ => ())

  // def update(user: UpdateUserRequest): DBIO[Unit] =
  //   UserTable.table
  //     .filter(acc => acc.id === user.id.getOrElse(""))
  //     .map(u => (u.name, u.surname, u.nickname))
  //     .update((user.name, user.surname, user.nickname))
  //     .map(_ => ())

  def findUser(userId: String): DBIO[Option[String]] =
    UserTable.table
      .filter(_.id === userId)
      .map(_.id)
      .result
      .headOption

}
