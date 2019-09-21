package info.ludwikowski.recorder

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://computer-database.gatling.io")
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("pl,en-US;q=0.7,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:69.0) Gecko/20100101 Firefox/69.0")





	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.get("/computers"))
		.pause(7)
		.exec(http("request_1")
			.get("/computers/new"))
		.pause(21)
		.exec(http("request_2")
			.post("/computers")
			.formParam("name", "asd")
			.formParam("introduced", "2019-01-01")
			.formParam("discontinued", "2019-01-01")
			.formParam("company", "1"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}