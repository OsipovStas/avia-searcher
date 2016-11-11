package com.epam.bigdata.osipov.avia

//import org.apache.hadoop.hbase.client.Put
//import org.apache.hadoop.hbase.io.ImmutableBytesWritable
//import org.apache.hadoop.hbase.util.Bytes
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Price(
                  value: Double,
                  destination: String,
                  origin: String,
                  departure: String,
                  returning: String,
                  actual: Boolean
                )

object Price {

  implicit val jsonFormat = {
    val jsonDescription =
      (__ \ "value").format[Double] and
        (__ \ "destination").format[String] and
        (__ \ "origin").format[String] and
        (__ \ "depart_date").format[String] and
        (__ \ "return_date").format[String] and
        (__ \ "actual").format[Boolean]

    jsonDescription(Price.apply, unlift(Price.unapply))
  }

  def parse(s: String): Price = {
    val data = s.split(";")
    Price(data(0).toDouble, data(1), data(2), data(3), data(4), data(5).toBoolean)
  }

  def toCsv(p: Price): String = {
    p.productIterator.mkString(",")
  }


}