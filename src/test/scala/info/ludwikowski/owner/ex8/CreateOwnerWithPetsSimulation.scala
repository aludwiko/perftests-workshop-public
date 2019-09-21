package info.ludwikowski.owner.ex8

import info.ludwikowski.base.PetClinicSimulation
import info.ludwikowski.owner.OwnerActions
import info.ludwikowski.owner.OwnerActions.{addPet, createOwner}
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateOwnerWithPetsSimulation extends PetClinicSimulation {

  val scn = scenario("Exercise 8")
    .exec(createOwner)
    .randomSwitch(
      70d -> exec(addPet),
      20d -> repeat(2) {
        exec(addPet)
      },
      10d -> repeat(3) {
        exec(addPet)
      }
    )

  setUp(scn.inject(atOnceUsers(10)).protocols(httpProtocol))
}
