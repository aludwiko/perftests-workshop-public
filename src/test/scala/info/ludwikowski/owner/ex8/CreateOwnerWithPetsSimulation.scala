package info.ludwikowski.owner.ex8

import info.ludwikowski.base.PetClinicSimulation
import info.ludwikowski.owner.OwnerActions.{addPet, createOwner}
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateOwnerWithPetsSimulation extends PetClinicSimulation {

  val ownerIdKey = "ownerId"

  val scn = scenario("Exercise 8")
    .exec(createOwner(ownerIdKey))
    .randomSwitch(
      70d -> exec(addPet(ownerIdKey)),
      20d -> repeat(2) {
        exec(addPet(ownerIdKey))
      },
      10d -> repeat(3) {
        exec(addPet(ownerIdKey))
      }
    )

  setUp(scn.inject(atOnceUsers(10)).protocols(httpProtocol))
}
