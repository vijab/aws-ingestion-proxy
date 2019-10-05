package com.vijai.aws.ingestion.producer.kinesis

import java.io.ByteArrayOutputStream

import collection.JavaConverters._
import com.sksamuel.avro4s.{AvroOutputStream, AvroSchema}
import com.typesafe.scalalogging.LazyLogging
import com.vijai.aws.ingestion.schema.Envelope
import software.amazon.awssdk.core.SdkBytes
import software.amazon.awssdk.services.kinesis.model.{PutRecordRequest, PutRecordsRequest, PutRecordsRequestEntry}

import scala.util.Random

trait EnvelopeKinesisAdapter extends LazyLogging {

  lazy val schema = AvroSchema[Envelope]

  lazy val PARTITION_COUNT = 5

  val random = new Random()

  private[this] def toBinary(envelope: Envelope): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    val os = AvroOutputStream.data[Envelope].to(baos).build(schema)
    os.write(envelope)
    os.close()
    baos.toByteArray
  }

  def fromEnvelope(envelopes: Array[Envelope], streamName: String): PutRecordsRequest = {
    val entries: Array[PutRecordsRequestEntry] = envelopes.map(e => {
      PutRecordsRequestEntry.builder()
        .partitionKey(s"partitionKey-${random.nextInt(PARTITION_COUNT)}")
        .data(SdkBytes.fromByteArray(toBinary(e)))
        .build()
    })
    PutRecordsRequest.builder()
        .streamName(streamName)
        .records(entries.toList.asJava)
        .build()
  }

  def fromEnvelope(envelope: Envelope, streamName: String): PutRecordRequest = {
    PutRecordRequest.builder()
      .data(SdkBytes.fromByteArray(toBinary(envelope)))
      .partitionKey(s"partitionKey-${random.nextInt(PARTITION_COUNT)}")
      .streamName(streamName)
      .build()
  }

}
