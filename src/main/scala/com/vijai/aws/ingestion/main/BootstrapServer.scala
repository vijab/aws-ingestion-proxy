package com.vijai.aws.ingestion.main

import java.security.Security

import akka.http.scaladsl.Http
import com.vijai.aws.ingestion.routes.Routes

import scala.concurrent.Future

object BootstrapServer extends App
  with DependencyInitializer
  with Routes
{

  Security.setProperty("networkaddress.cache.ttl", "30")

  val host = config.getString("server.host")
  val port: Int = config.getInt("server.port")

  logger.info("Starting up")

  private val bindingFuture: Future[Http.ServerBinding] = {
    logger.info(s"Starting up server, binding to host, $host and port, $port")
    Http().bindAndHandle(routes, host, port)
  }

}
