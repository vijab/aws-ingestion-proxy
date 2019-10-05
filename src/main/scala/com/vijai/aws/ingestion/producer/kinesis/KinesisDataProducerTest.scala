package com.vijai.aws.ingestion.producer.kinesis
import java.util.stream.IntStream

import com.vijai.aws.ingestion.schema.{Envelope, Metadata, Spans}
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.kinesis.KinesisClient

object KinesisDataProducerTest extends App {

  val kinesisDataProducer = new KinesisDataProducer {
    override val awsKinesisClient: KinesisClient = KinesisClient.builder().credentialsProvider(EnvironmentVariableCredentialsProvider.create())
      .region(Region.EU_WEST_1).build()
  }

  val envelope = Envelope(
    metadata = Metadata(eventType = "eventType1", eventTypeVersion = 1, fingerprint = "fp1"),
    spans = Spans(List.empty),
    payload = "somePayload".getBytes
  )

  (1 to 1000).map(i => {
    val response = kinesisDataProducer.produce(envelope)
    println(response.message)
    response
  })

}
