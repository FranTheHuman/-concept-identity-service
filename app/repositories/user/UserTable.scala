package repositories.user

import models.domain.User
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import java.sql.Timestamp
import java.time.LocalDateTime

class UserTable(tag: Tag) extends Table[User](tag, "users") {

  def id: Rep[String] = column[String]("id", O.PrimaryKey)

  def createdUtc: Rep[Timestamp] = column[Timestamp]("created_utc")

  override def * : ProvenShape[User] =
    (id, createdUtc).<>(
      { case (id, createdUtc) =>
        User(id, Some(createdUtc))
      },
      { u: User =>
        Some(
          (
            u.id,
            u.createdUtc.getOrElse(Timestamp.valueOf(LocalDateTime.now()))
          )
        )
      }
    )
}

object UserTable {
  val table = TableQuery[UserTable]
}
