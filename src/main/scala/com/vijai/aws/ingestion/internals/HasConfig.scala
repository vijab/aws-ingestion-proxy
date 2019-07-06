package com.vijai.aws.ingestion.internals

import com.typesafe.config.{Config, ConfigFactory}


trait HasConfig {

  val config: Config

}
