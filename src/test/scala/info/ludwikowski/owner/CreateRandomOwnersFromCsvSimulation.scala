package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import info.ludwikowski.owner.OwnerActions.createOwner
import io.gatling.core.Predef._

class CreateRandomOwnersFromCsvSimulation extends PetClinicSimulation {

  private val ownerIdKey = "ownerId"
  private val firstNameKey = "firstName"
  private val lastNameKey = "lastName"
  private val cityKey = "city"

  val scn = scenario("Exercise 9")
    .feed(csv("owners_data.csv").circular)
    .exec(createOwner(ownerIdKey, firstNameKey, lastNameKey, cityKey))

  setUp(scn.inject(atOnceUsers(5)).protocols(httpProtocol))
}
