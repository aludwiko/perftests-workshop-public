package info.ludwikowski.vet.ex3

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
      .check(regex(".*lastName\":\"(.*?)\"").is("Ludwikowski"))
      .check(jsonPath("$..lastName").is("Ludwikowski"))
      .check(jsonPath("$..specialties[*].name").findAll.is(Seq("radiology")))
    )
    .exec(http("create vet with wrong speciality")
      .post("/api/vets")
      .body(StringBody(
        """
          |{
          |  "id":null,
          |  "firstName":"Andrzej2",
          |  "lastName":"Ludwikowski2",
          |  "specialties":[
          |    {
          |      "id":123,
          |      "name":"lalalala"
          |    }
          |  ]
          |}
          |""".stripMargin)).asJson
      .check(status.is(400))
    )

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
