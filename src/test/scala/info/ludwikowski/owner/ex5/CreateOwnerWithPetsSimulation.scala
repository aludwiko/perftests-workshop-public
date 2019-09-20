package info.ludwikowski.owner.ex5

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

case class PetType(id: Int, name: String)

class CreateOwnerWithPetsSimulation extends PetClinicSimulation {

  val petTypes = List(PetType(1, "cat"), PetType(2, "dog"), PetType(3, "lizard"), PetType(4, "snake"), PetType(5, "hamster"))

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
    .foreach(Random.shuffle(petTypes).take(2), "petType") {
      exec(http("add pet for owner with id ${ownerId}")
        .post("/api/pets")
        .body(StringBody(session => {
          val petTypeFromSession = session("petType").as[PetType]
          s"""
             |{
             |  "id":null,
             |  "owner":{
             |    "id":${session("ownerId").as[String]},
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
             |  "birthDate":"2019\\/09\\/05",
             |  "type":{
             |    "id":${petTypeFromSession.id},
             |    "name":"${petTypeFromSession.name}"
             |  }
             |}
             |""".stripMargin
        })).asJson
        .check(status.is(201)))
    }

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
