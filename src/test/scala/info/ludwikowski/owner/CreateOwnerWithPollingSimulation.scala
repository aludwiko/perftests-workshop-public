package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.commons.validation.{Failure, Success, Validation}
import io.gatling.core.Predef._
import io.gatling.core.check.Validator
import io.gatling.http.Predef._

import scala.concurrent.duration._

class CreateOwnerWithPollingSimulation extends PetClinicSimulation {

  val scn = scenario("Polling")
    .exec(polling.every(50 millis)
      .exec(http("get owners")
        .get("/api/owners")
        .check(status.is(200)))
    )
    .pause(10 seconds)
    .exec(
      http("create owner")
        .post("/api/owners")
        .body(StringBody(
          """
            |{
            |  "id":null,
            |  "firstName":"Andrzej",
            |  "lastName":"Ludwikowski",
            |  "address":"address",
            |  "city":"Wrocław",
            |  "telephone":"123123123"
            |}
            |""".stripMargin)).asJson
        .check(status.is(201))
    )
    .exec(polling.stop)

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
