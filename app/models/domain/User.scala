package models.domain

import java.sql.Timestamp

case class User(
    id: String,
    createdUtc: Option[Timestamp] = None
)
