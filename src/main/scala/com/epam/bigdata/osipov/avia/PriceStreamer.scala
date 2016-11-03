package com.epam.bigdata.osipov.avia

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object PriceStreamer {

  def main(args: Array[String]): Unit = {
    val cfg = new SparkConf()
      .setAppName("Price Streamer")
    val sc = new SparkContext(cfg)
    val ssc = new StreamingContext(sc, Seconds(10))

    ssc.receiverStream(new AviaSalesReceiver).foreachRDD {
      rdd =>
        rdd.foreach {
          case p @ Price(v, o, r) =>
            val k = createKafkaProducer()
            k.send(new ProducerRecord[String, String]("prices", p.toString))
        }
    }

    ssc.start()

    ssc.awaitTermination()
  }

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
