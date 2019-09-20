package info.ludwikowski.owner.ex6

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateOwnerWithPetsSimulation extends PetClinicSimulation {

  val scn = scenario("Exercise 6")
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
    .asLongAs(session => session("counter").asOption[Int].getOrElse(0) < 4) {
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
        .exec(session => {
          val counter = session("counter").asOption[Int]
          counter match {
            case Some(c) => session.set("counter", c + 1)
            case None => session.set("counter", 1)
          }
        })
    }

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
