package info.ludwikowski.vet.ex1

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetVetsSimulation extends PetClinicSimulation {

  val scn = scenario("Exercise 1")
    .exec(http("get vets")
      .get("/api/vets")
      .check(status.is(200)))


  setUp(scn.inject(atOnceUsers(10)).protocols(httpProtocol))
}
