package com.vijai.aws.ingestion.main

import java.io.ByteArrayOutputStream

import com.sksamuel.avro4s.{AvroOutputStream, AvroSchema}
import com.vijai.aws.ingestion.schema.{Envelope, Metadata, Span, Spans}


object AvroTest extends App {

  val schema = AvroSchema[Envelope]

  val envelope = Envelope(
    metadata = Metadata(eventType = "eventType1", eventTypeVersion = 1, fingerprint = "fp1"),
    spans = Spans(List.empty),
    payload = "somePayload".getBytes
  )

  val baos = new ByteArrayOutputStream()
  val os = AvroOutputStream.data[Envelope].to(baos).build(schema)
  os.write(envelope)
  os.close()
  println(new String(baos.toByteArray))
}