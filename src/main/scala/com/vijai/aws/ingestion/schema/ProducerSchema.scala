package com.vijai.aws.ingestion.schema

case class Span(operationName: String, startTime: Long, endTime: Long)

case class Spans(spans: List[Span])

case class Metadata(eventType: String, eventTypeVersion: Int, fingerprint: String)

case class Envelope(metadata: Metadata,
                   spans: Spans,
                   payload: Array[Byte])

case class BulkProducerResponse(producerResponse: Array[ProducerResponse])

case class ProducerResponse(message: String, isSuccessful: Boolean, errorCode: Option[Int])