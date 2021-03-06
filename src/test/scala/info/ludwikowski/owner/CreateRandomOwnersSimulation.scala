package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import info.ludwikowski.owner.OwnerActions.createOwner
import io.gatling.core.Predef._

import scala.util.Random

class CreateRandomOwnersSimulation extends PetClinicSimulation {

  val cities = List("Wrocław", "Warszawa", "Zielona Góra")

  private val ownerIdKey = "ownerId"
  private val firstNameKey = "firstName"
  private val lastNameKey = "lastName"
  private val cityKey = "city"

  val randomOwners = Iterator.continually(
    Map(
      firstNameKey -> s"Andrzej_${Random.alphanumeric.take(4).mkString}",
      lastNameKey -> s"Ludwikowski_${Random.alphanumeric.take(4).mkString}",
      cityKey -> cities(Random.nextInt(cities.size))
    )
  )

  val scn = scenario("Exercise 9")
    .feed(randomOwners)
    .exec(createOwner(ownerIdKey, firstNameKey, lastNameKey, cityKey))

  setUp(scn.inject(atOnceUsers(5)).protocols(httpProtocol))
}
