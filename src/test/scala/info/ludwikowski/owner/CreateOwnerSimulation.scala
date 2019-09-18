package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateOwnerSimulation extends PetClinicSimulation {

  val scn = scenario("Exercise 2")
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

  setUp(scn.inject(atOnceUsers(5)).protocols(httpProtocol))
}
