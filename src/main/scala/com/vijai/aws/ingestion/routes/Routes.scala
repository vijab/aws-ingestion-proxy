package com.vijai.aws.ingestion.routes

import akka.http.scaladsl.server.Directives._

trait Routes extends PingRoute with ProxyRoute {

  val routes = ping ~ produceOnTopic

}
