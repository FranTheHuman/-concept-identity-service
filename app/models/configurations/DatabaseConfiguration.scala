package models.configurations

case class DatabaseConfiguration(
  driver: String,
  url: String,
  user: String,
  password: String
)
