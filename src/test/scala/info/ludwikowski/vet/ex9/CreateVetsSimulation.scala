package info.ludwikowski.vet.ex9

import info.ludwikowski.base.PetClinicSimulation
import info.ludwikowski.vet.VetActions.createVet
import io.gatling.core.Predef._

import scala.util.Random

class CreateVetsSimulation extends PetClinicSimulation {

  private val firstNameKey = "firstName"
  private val lastNameKey = "lastName"
  private val specialityIdKey = "specialityId"
  private val specialityNameKey = "specialityName"

  private val specialities = List((1, "radiology"), (2, "surgery"), (3, "dentistry"))

  val randomOwners = Iterator.continually({
    val speciality = specialities(Random.nextInt(specialities.size))
    Map(
      firstNameKey -> s"Andrzej_${Random.alphanumeric.take(4).mkString}",
      lastNameKey -> s"Ludwikowski_${Random.alphanumeric.take(4).mkString}",
      specialityIdKey -> speciality._1,
      specialityNameKey -> speciality._2
    )
  })

  val scnWithRandom = scenario("Exercise 9 random")
    .feed(randomOwners)
    .exec(createVet(firstNameKey, lastNameKey, specialityIdKey, specialityNameKey))

  val scnWithCsv = scenario("Exercise 9 csv")
    .feed(csv("vets_data.csv").circular)
    .exec(createVet(firstNameKey, lastNameKey, specialityIdKey, specialityNameKey))

  setUp(scnWithRandom.inject(atOnceUsers(10)).protocols(httpProtocol),
    scnWithCsv.inject(atOnceUsers(5)).protocols(httpProtocol))
}
