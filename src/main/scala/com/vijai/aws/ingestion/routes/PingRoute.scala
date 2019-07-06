package com.vijai.aws.ingestion.routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.LazyLogging

trait PingRoute extends LazyLogging{

  val ping: Route = path("ping") {
    get {
      logger.debug("Received ping.")
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "pong"))
    }
  }

}
