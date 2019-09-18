package info.ludwikowski.vet.ex2

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateVetSimulation extends PetClinicSimulation {

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
