package info.ludwikowski.vet.ex4

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateAndGetVetSimulation extends PetClinicSimulation {

  val specialityId = 3
  val specialityName = "dentistry"

  val scn = scenario("Exercise 2")
    .exec(http("create vet")
      .post("/api/vets")
      .body(StringBody(
        """
          |{
          |  "id":null,
          |  "firstName":"Antoni",
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
      .check(jsonPath("$..id").exists.saveAs("vetId"))
      .check(jsonPath("$..firstName").saveAs("vetFirstName"))
      .check(jsonPath("$..lastName").saveAs("vetLastName"))
    )
    .exec(http("update speciality")
      .put("/api/vets/${vetId}")
      .body(StringBody(
        s"""
          |{
          |  "id":$${vetId},
          |  "firstName":"$${vetFirstName}",
          |  "lastName":"$${vetLastName}",
          |  "specialties":[
          |    {
          |      "id":1,
          |      "name":"radiology"
          |    },
          |    {
          |      "id":${specialityId},
          |      "name":"${specialityName}"
          |    }
          |  ]
          |}
          |""".stripMargin)).asJson
      .check(status.is(204))
    )
    .exec(http("get wet")
      .get("/api/vets/${vetId}")
      .check(jsonPath("$..specialties[*].name").findAll.is(Seq("dentistry","radiology")))
    )

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
