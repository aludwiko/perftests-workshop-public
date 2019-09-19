package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateAndGetOwnerSimulation extends PetClinicSimulation {

  val scn = scenario("Exercise 2")
    .exec(
      http("create owner")
        .post("/api/owners")
        .body(StringBody(
          """
            |{
            |  "id":null,
            |  "firstName":"Antoni",
            |  "lastName":"Ludwikowski",
            |  "address":"address",
            |  "city":"Wrocław",
            |  "telephone":"123123123"
            |}
            |""".stripMargin)).asJson
        .check(status.is(201))
        .check(jsonPath("$..id").exists.saveAs("ownerId"))
    )
    .exec(
      http("get owner")
        .get("/api/owners/${ownerId}")
        .check(status.is(200))
        .check(jsonPath("$..firstName").is("Antoni"))
    )

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
