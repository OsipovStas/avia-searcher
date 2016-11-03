import com.epam.bigdata.osipov.avia.Price
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsArray, _}
import play.api.libs.ws.ning.NingWSClient

import scala.concurrent.ExecutionContext.Implicits.global






object AviaSalesApiUseCaseExample {

  // Instantiation of the client
  // In a real-life application, you would instantiate one, share it everywhere,
  // and call wsClient.close() when you're done
  def main(args: Array[String]): Unit = {
    val wsClient: NingWSClient = NingWSClient()
    wsClient
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
      .get()
      .map { wsResponse =>
        if (!(200 to 299).contains(wsResponse.status)) {
          sys.error(s"Received unexpected status ${wsResponse.status} : ${wsResponse.body}")
        }
        Json.parse(wsResponse.body).\("data").get.asInstanceOf[JsArray].value.foreach(v => println(s"Received ticket ${Json.fromJson[Price](v).get}"))
        println(s"The response header Content-Length was ${wsResponse.header("Content-Length")}")
        wsClient.close()
      }
  }

}
