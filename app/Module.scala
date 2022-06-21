import com.google.inject.{AbstractModule, Provides}
import com.typesafe.config.ConfigFactory
import models.configurations.{AuthProviderConfiguration, AuthTokenConfiguration, DatabaseConfiguration}
import play.api.{Configuration, Environment}

class Module(environment: Environment, configuration: Configuration) extends AbstractModule {

  private val configFactory =
    ConfigFactory.load()

  // val _ = (environment, configuration)  // Just to not break at compile time for not using the environment field.

  override def configure(): Unit = {}

  @Provides
  def authorizationTokenConf(): AuthTokenConfiguration =
    AuthTokenConfiguration(publicKey = configFactory.getString("authorization.token.public_key"))

  @Provides
  def authorizationProviderConf(): AuthProviderConfiguration =
    AuthProviderConfiguration(configFactory.getString("auth_provider.url"))

  @Provides
  def databaseConfiguration(): DatabaseConfiguration =
    DatabaseConfiguration(
      driver = configFactory.getString("slick.dbs.default.driver"),
      url = configFactory.getString("slick.dbs.default.db.url"),
      user = configFactory.getString("slick.dbs.default.db.user"),
      password = configFactory.getString("slick.dbs.default.db.password")
    )

}
