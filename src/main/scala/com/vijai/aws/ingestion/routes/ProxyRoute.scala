package com.vijai.aws.ingestion.routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive1, Route}
import com.typesafe.scalalogging.LazyLogging
import com.vijai.aws.ingestion.internals._
import com.vijai.aws.ingestion.schema.JsonProducerRequest

trait ProxyRoute extends LazyLogging with HasConfig {

  final case class TopicParams(topic: String, contentType: ContentType, producerRecordFormat: Option[KafkaMessageFormat]) {
    require(!topic.isEmpty)
    require(contentType.mediaType.isApplication)
    require(producerRecordFormat.isDefined)
  }

  def extractContentType: Directive1[ContentType] = extractRequestEntity.map(_.contentType)

  private[this] def embeddedFormat(contentType: Option[String]): Option[KafkaMessageFormat] = {
    logger.debug(s"embedded format: ${contentType}")
    contentType match {
      case Some("application/vnd.kafka.binary.v2+json") => logger.info("binary"); Option(BinaryFormat)
      case Some("application/vnd.kafka.avro.v2+json") => logger.info("avro"); Option(AvroFormat)
      case Some("application/vnd.kafka.json.v2+json") => logger.info("json"); Option(JsonFormat)
      case _ => logger.warn("No X-Content-Type header specified, assuming JSON records."); Option(JsonFormat)
    }
  }

  val produceOnTopic: Route =
    (path("produce"/Segment) & extractContentType & optionalHeaderValueByName("X-Content-Type").as(embeddedFormat)).as(TopicParams) { topic =>
      post {
        import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
        import io.circe.generic.auto._
        entity(as[JsonProducerRequest]) { r =>
          logger.info(s"From message, ${r.records}")
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"Received POST"))
        }
      }
    }

}
