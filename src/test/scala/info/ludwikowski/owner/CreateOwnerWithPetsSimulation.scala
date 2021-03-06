package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateOwnerWithPetsSimulation extends PetClinicSimulation {

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
        .check(jsonPath("$..id").exists.saveAs("ownerId"))
    )
    .repeat(4) {
      exec(http("add pet for owner with id ${ownerId}")
        .post("/api/pets")
        .body(StringBody(
          """
            |{
            |  "id":null,
            |  "owner":{
            |    "id":${ownerId},
            |    "firstName":"Andrzej",
            |    "lastName":"Ludwikowski",
            |    "address":"address",
            |    "city":"Wrocław",
            |    "telephone":"123123123",
            |    "pets":[
            |
            |    ]
            |  },
            |  "name":"rex",
            |  "birthDate":"2019\/09\/05",
            |  "type":{
            |    "id":2,
            |    "name":"dog"
            |  }
            |}
            |""".stripMargin)).asJson
        .check(status.is(201)))
    }

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
