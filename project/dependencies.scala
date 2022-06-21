import sbt._

object Dependencies {

  object V {
    val scalatestplusPlay = "5.1.0"
    val playSlick         = "5.0.0"
    val postgresql        = "42.3.4"
    val circe             = "0.14.1"
    val playCirce         = "2814.2"
    val catsEffect        = "3.3.11"
    val catsCore          = "2.7.0"
    val jwtScala          = "9.0.5"
    val logs4cats         = "2.3.0"
    val logback           = "1.2.11"
  }

  val scalatestplusPlay   = "org.scalatestplus.play" %% "scalatestplus-play"    % V.scalatestplusPlay % Test
  val playSlick           = "com.typesafe.play"      %% "play-slick"            % V.playSlick
  val playSlickEvolutions = "com.typesafe.play"      %% "play-slick-evolutions" % V.playSlick
  val postgresql          = "org.postgresql"          % "postgresql"            % V.postgresql
  val circeCore           = "io.circe"               %% "circe-core"            % V.circe
  val circeGenerics       = "io.circe"               %% "circe-generic"         % V.circe
  val circeParser         = "io.circe"               %% "circe-parser"          % V.circe
  val playCirce           = "com.dripower"           %% "play-circe"            % V.playCirce
  val catsEffect          = "org.typelevel"          %% "cats-effect"           % V.catsEffect
  val catsCore            = "org.typelevel"          %% "cats-core"             % V.catsCore
  val log4catsCore        = "org.typelevel"          %% "log4cats-core"         % V.logs4cats
  val log4Slf4j           = "org.typelevel"          %% "log4cats-slf4j"        % V.logs4cats
  val jwtScalaCirce       = "com.github.jwt-scala"   %% "jwt-circe"             % V.jwtScala
  val jwtScalaCore        = "com.github.jwt-scala"   %% "jwt-core"              % V.jwtScala
  val logbackClassic      = "ch.qos.logback"          % "logback-classic"       % V.logback

  val dependencies: Seq[ModuleID] = Seq(
    scalatestplusPlay,
    playSlick,
    playSlickEvolutions,
    postgresql,
    circeCore,
    circeGenerics,
    circeParser,
    playCirce,
    catsEffect,
    catsCore,
    jwtScalaCirce,
    jwtScalaCore,
    log4catsCore,
    log4Slf4j,
    logbackClassic % Runtime
  )

}
