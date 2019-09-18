package info.ludwikowski.vet.ex2

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateVetSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:9966/petclinic") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Exercise 2")
    .exec(http("create vet")
      .post("/api/vets")
      .body(StringBody(
        """
          |{
          |  "id":null,
          |  "firstName":"Andrzej",
          |  "lastName":"Ludwikowski",
          |  "specialties":[
          |    {
          |      "id":1,
          |      "name":"radiology"
          |    }
          |  ]
          |}
          |""".stripMargin)).asJson
      .check(status.is(201))
    )

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
