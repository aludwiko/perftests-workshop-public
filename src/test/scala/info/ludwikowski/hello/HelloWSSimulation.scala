package info.ludwikowski.hello

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class HelloWSSimulation extends Simulation {

  val wsProtocol = http
    .wsBaseUrl("wss://echo.websocket.org")


  val scn = scenario("Exercise 11")
    .exec(ws("Connect WS").connect(""))
    .repeat(10) {
      exec(ws("Send message")
        .sendText("Andrzej")
        .await(1 second)(ws.checkTextMessage("checkName")
          .check(regex("Andrzej").saveAs("response"))))
    }
    .exec(ws("Close WS").close)


  setUp(scn.inject(constantUsersPerSec(5).during(30 seconds)).protocols(wsProtocol))

}
