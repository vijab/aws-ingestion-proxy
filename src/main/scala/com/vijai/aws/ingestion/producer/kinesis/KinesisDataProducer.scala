package com.vijai.aws.ingestion.producer.kinesis

import com.typesafe.scalalogging.LazyLogging
import com.vijai.aws.ingestion.producer.DataProducer
import com.vijai.aws.ingestion.schema.{BulkProducerResponse, Envelope, ProducerResponse}
import software.amazon.awssdk.services.kinesis.KinesisClient
import collection.JavaConverters._

trait KinesisDataProducer extends LazyLogging
  with EnvelopeKinesisAdapter
  with DataProducer {

  val awsKinesisClient: KinesisClient

  val streamName = "vj-kinesis-stream"

  override def produce(record: Envelope): ProducerResponse = {
    val response = awsKinesisClient.putRecord(fromEnvelope(envelope = record, streamName))
    ProducerResponse(response.toString, false, Option.empty)
  }

  override def produce(records: Array[Envelope]): BulkProducerResponse = {
    val response = awsKinesisClient.putRecords(fromEnvelope(envelopes = records, streamName))
    val responses = response.records().asScala.map(r => {
      ProducerResponse(r.toString, false, Option.empty)
    }).toArray
    BulkProducerResponse(responses)
  }


}
