package com.epam.bigdata.osipov.avia

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Price(value: Double, departure: String, destination: String)

object Price {

  implicit val jsonFormat = {
    val jsonDescription =
      (__ \ "value").format[Double] and
        (__ \ "origin").format[String] and
        (__ \ "destination").format[String]

    jsonDescription(Price.apply, unlift(Price.unapply))
  }

}