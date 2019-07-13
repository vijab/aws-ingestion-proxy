organization := "com.vijai"

name := "aws-ingestion-proxy"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.6"

libraryDependencies ++= {
  val circeVersion = "0.9.3"
  val scalaLoggingVersion = "3.8.0"
  val kafkaVersion = "1.1.1"
  val akkaHttpVersion = "10.1.4"
  val akkaVersion = "2.5.12"
  val macwireVersion = "2.3.1"

  Seq(
    // typesafe
    "com.typesafe" % "config" % "1.3.3",
    // logging
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.21",
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "net.logstash.logback" % "logstash-logback-encoder" % "4.8",
    // kafka
    "org.apache.kafka" %% "kafka" % kafkaVersion,
    // akka http
    "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-circe" % "1.21.0",
    // macwire
    "com.softwaremill.macwire" %% "macros" % macwireVersion % "provided",
    "com.softwaremill.macwire" %% "util" % macwireVersion,
    "com.softwaremill.macwire" %% "proxy" % macwireVersion,
    // circe
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic-extras" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "io.circe" %% "circe-java8" % circeVersion,
    "io.circe" %% "circe-optics" % circeVersion,
    // TEST
    "org.scalatest" %% "scalatest" % "3.0.1" % Test,
    "org.mockito" % "mockito-all" % "1.10.19" % Test
  )

}

Revolver.settings

fork in Test := true

fork in run := true

javaOptions in reStart ++= Seq("-Dlogback.configurationFile=logback.xml", "-Dconfig.file=src/main/resources/application-dev.conf")

javaOptions in run ++= Seq("-Dlogback.configurationFile=logback.xml", "-Dconfig.file=src/main/resources/application.conf")

dockerExposedPorts := Seq(8080)
dockerUpdateLatest := true
dockerRepository := Some("199982716068.dkr.ecr.eu-west-1.amazonaws.com") // TODO make this configurable.

enablePlugins(JavaAppPackaging)

scalacOptions in compile ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8", // yes, this is 2 args
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xlint:missing-interpolator",
  "-Xfuture"
)