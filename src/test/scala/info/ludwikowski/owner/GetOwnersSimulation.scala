package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetOwnersSimulation extends PetClinicSimulation {

  val scn = scenario("Exercise 1")
    .exec(http("get owners")
      .get("/api/owners")
    .check(status.is(200)))

  setUp(scn.inject(atOnceUsers(10)).protocols(httpProtocol))
}
