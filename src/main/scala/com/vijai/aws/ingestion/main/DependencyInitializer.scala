package com.vijai.aws.ingestion.main

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import com.vijai.aws.ingestion.internals.HasConfig

trait DependencyInitializer extends LazyLogging with HasConfig {

  implicit val system = ActorSystem("kafka-http-proxy")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  lazy val config: Config = ConfigFactory.defaultApplication()

}
