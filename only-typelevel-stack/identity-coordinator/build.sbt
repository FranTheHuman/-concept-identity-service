name         := "identity-coordinator"
version      := "0.1.0-SNAPSHOT"
scalaVersion := "3.1.2"

mainClass := Some("Main")

enablePlugins(DockerPlugin, JavaAppPackaging)

packageName          := "identity-coordinator"
dockerExposedPorts   := Seq(8080)
dockerEntrypoint     := Seq("/opt/docker/bin/boot")
dockerExposedVolumes := Seq("/opt/docker/logs", "/opt/docker/bin")
dockerBaseImage      :=  "openjdk:11"
