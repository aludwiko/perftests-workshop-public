package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class CreateRandomOwnersSimulation extends PetClinicSimulation {

  val cities = List("Wrocław", "Warszawa", "Zielona Góra")

  val randomOwners = Iterator.continually(
    Map(
      "firstName" -> s"Andrzej_${Random.alphanumeric.take(4).mkString}",
      "lastName" -> s"Ludwikowski_${Random.alphanumeric.take(4).mkString}",
      "city" -> cities(Random.nextInt(cities.size))
    )
  )

  val scn = scenario("Exercise 9")
    .feed(randomOwners)
    .exec(
      http("create owner")
        .post("/api/owners")
        .body(StringBody(
          """
            |{
            |  "id":null,
            |  "firstName":"${firstName}",
            |  "lastName":"${lastName}",
            |  "address":"address",
            |  "city":"${city}",
            |  "telephone":"123123123"
            |}
            |""".stripMargin)).asJson
        .check(status.is(201))
    )

  setUp(scn.inject(atOnceUsers(5)).protocols(httpProtocol))
}
