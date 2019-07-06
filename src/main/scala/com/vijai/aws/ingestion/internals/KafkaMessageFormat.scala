package com.vijai.aws.ingestion.internals

sealed trait KafkaMessageFormat

final case object JsonFormat extends KafkaMessageFormat

final case object BinaryFormat extends KafkaMessageFormat

final case object AvroFormat extends KafkaMessageFormat
