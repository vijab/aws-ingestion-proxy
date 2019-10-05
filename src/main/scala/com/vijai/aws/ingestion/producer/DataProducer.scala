package com.vijai.aws.ingestion.producer

import com.vijai.aws.ingestion.schema.{BulkProducerResponse, Envelope, ProducerResponse}

trait DataProducer {

  def produce(records: Array[Envelope]): BulkProducerResponse

  def produce(record: Envelope): ProducerResponse

}
