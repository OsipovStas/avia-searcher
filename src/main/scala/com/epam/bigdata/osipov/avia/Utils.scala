package com.epam.bigdata.osipov.avia

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}

object Utils {

  def createKafkaProducer() = {
    val props = new java.util.HashMap[String, AnyRef]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")

    new KafkaProducer[String, String](props)
  }



}
