package com.epam.bigdata.osipov.avia

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import Utils._


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



}
