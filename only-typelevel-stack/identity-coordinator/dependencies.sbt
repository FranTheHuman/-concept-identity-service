libraryDependencies += "com.softwaremill.sttp.client3" %% "core" % "3.6.2"
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.11"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.7.0"
libraryDependencies += "com.typesafe" % "config" % "1.4.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.11"

val jwtScala = "9.0.5"

libraryDependencies ++= Seq(
  "com.github.jwt-scala" %% "jwt-circe",
  "com.github.jwt-scala" %% "jwt-core"
).map(_ % jwtScala)

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
