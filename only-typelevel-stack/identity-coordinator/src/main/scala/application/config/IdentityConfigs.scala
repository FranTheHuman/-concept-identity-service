package application.config

import com.typesafe.config.{Config, ConfigFactory}

class IdentityConfigs:

  val loadConf: Config = ConfigFactory.load()

  val ACCESS_TOKEN_PATH = "access_token"

  def PUBLIC_KEY: String = loadConf.getString(ACCESS_TOKEN_PATH + ".public_key")

  val SERVER_PATH = "server"

  def SERVER_HOST: String    = loadConf.getString(SERVER_PATH + ".host")
  def SERVER_PORT: Int       = loadConf.getInt(SERVER_PATH + ".port")
  def SERVER_BODY: Boolean   = loadConf.getBoolean(SERVER_PATH + ".log_body")
  def SERVER_HEADER: Boolean = loadConf.getBoolean(SERVER_PATH + ".log_headers")

