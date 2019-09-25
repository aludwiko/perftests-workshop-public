package info.ludwikowski.owner

import info.ludwikowski.owner.ex5.PetType

import scala.util.Random

object OwnerFeeder {

  val cities = List("Wrocław", "Warszawa", "Zielona Góra")

  val petTypes = List(PetType(1, "cat"), PetType(2, "dog"), PetType(3, "lizard"), PetType(4, "snake"), PetType(5, "hamster"))

  val ownerIdKey = "ownerId"
  val firstNameKey = "firstName"
  val lastNameKey = "lastName"
  val cityKey = "city"
  val petIdKey = "petId"
  val petNameKey = "petName"

  val ownerFeeder = Iterator.continually({
    val pet = petTypes(Random.nextInt(petTypes.size))
    Map(
      firstNameKey -> s"Andrzej_${Random.alphanumeric.take(4).mkString}",
      lastNameKey -> s"Ludwikowski_${Random.alphanumeric.take(4).mkString}",
      cityKey -> cities(Random.nextInt(cities.size)),
      petIdKey -> pet.id,
      petNameKey -> pet.name
    )
  })
}
