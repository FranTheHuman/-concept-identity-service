import Dependencies._

name := """Conceptual-identity-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(guice, ws)
libraryDependencies ++= dependencies
