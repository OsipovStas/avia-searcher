package com.epam.bigdata.osipov.avia

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import play.api.libs.json.{JsArray, Json}
import play.api.libs.ws.WSResponse
import play.api.libs.ws.ning.NingWSClient
import scala.concurrent.ExecutionContext.Implicits.global

class AviaSalesReceiver extends Receiver[Price](StorageLevel.MEMORY_AND_DISK_2) {

  val errbuf = new java.lang.StringBuilder()
  var client: NingWSClient = _

  override def onStart(): Unit = {
    client = NingWSClient()
    new Thread("Avia sales receiver") {
      override def run() {
        while(true) {
          receive()
          Thread.sleep(5000)
        }
      }
    }.start()
  }

  def request(client: NingWSClient = client) = {
    client
      .url("http://api.travelpayouts.com/v2/prices/latest")
      .withQueryString(
        "currency" -> "usd",
        "period_type" -> "year",
        "page" -> "1",
        "limit" -> "30",
        "show_to_affiliates" -> "true",
        "sorting" -> "price",
        "trip_class" -> "0",
        "token" -> "fb8344314839553791a309525487b41b")
  }

  def receive(): Unit = {
    request().get().map {
      response =>
        if (!(200 to 299).contains(response.status)) {
          sys.error(s"Received unexpected status ${response.status} : ${response.body}")
        }
        extractFlightsJSValues(response).foreach(v => store(Json.fromJson[Price](v).get))
    }
  }

  def extractFlightsJSValues(response: WSResponse) = {
    Json.parse(response.body).\("data").get.asInstanceOf[JsArray].value
  }

  override def onStop(): Unit = {
    client.close()
  }
}
