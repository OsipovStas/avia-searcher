import java.util.Calendar

import com.epam.bigdata.osipov.avia.{Utils, PriceStreamer, Price}
import org.apache.kafka.clients.producer.ProducerRecord
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsArray, _}
import play.api.libs.ws.ning.NingWSClient
import Utils._
import scala.concurrent.ExecutionContext.Implicits.global






object AviaSalesApiUseCaseExample {

  var m: Set[Price] = Set()
  val wsClient: NingWSClient = NingWSClient()
  val producer = createKafkaProducer()

  def main(args: Array[String]): Unit = {
    while(true) {
      makeRequest()
      println("Next request")
      Thread.sleep(60000)
    }
  }

  def makeRequest() = {
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
        Json.parse(wsResponse.body).\("data").get.asInstanceOf[JsArray].value.map(v => Json.fromJson[Price](v).get).foreach{
          case p if !m.contains(p)=>
            producer.send(new ProducerRecord[String, String]("prices", s"Received new price $p at ${Calendar.getInstance().getTime}"))
            m = m + p
        }
      }

  }

}
