package com.vijai.aws.ingestion.schema

import io.circe.Json

trait KafkaProducerRecord[K, V] {

  def getKey: Option[K]

  def getValue: V

  def getPartition: Option[Int]

}

final case class BinaryKafkaProducerRecord(key: Option[String], value: String, partition: Option[Int]) extends KafkaProducerRecord[Array[Byte], Array[Byte]] {
  override def getKey: Option[Array[Byte]] = key.map(_.getBytes)

  override def getValue: Array[Byte] = value.getBytes

  override def getPartition: Option[Int] = partition
}


final case class JsonKafkaProducerRecord(key: Option[Json], value: Json, partition: Option[Int]) extends KafkaProducerRecord[Json, Json] {
  override def getKey: Option[Json] = key

  override def getValue: Json = value

  override def getPartition: Option[Int] = partition
}

final case class BinaryProducerRequest(records: Seq[BinaryKafkaProducerRecord])

final case class JsonProducerRequest(records: Seq[JsonKafkaProducerRecord])

final case class AvroProducerRequest(key_schema: Option[String], key_schema_id: Option[Int], value_schema: Option[String], value_schema_id: Option[Int], records: Seq[JsonKafkaProducerRecord])