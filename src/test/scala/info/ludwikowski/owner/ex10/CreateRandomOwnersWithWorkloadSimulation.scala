package info.ludwikowski.owner.ex10

import info.ludwikowski.base.PetClinicSimulation
import info.ludwikowski.owner.OwnerActions.{addPet, createOwner}
import info.ludwikowski.owner.OwnerFeeder._
import io.gatling.core.Predef._

import scala.concurrent.duration._

class CreateRandomOwnersWithWorkloadSimulation extends PetClinicSimulation {

  val scn = scenario("Exercise 10")
    .feed(ownerFeeder)
    .exec(createOwner(ownerIdKey, firstNameKey, lastNameKey, cityKey))
    .group("create pet") {
      randomSwitch(
        70d -> exec(addPet(ownerIdKey, petIdKey, petNameKey)),
        20d -> repeat(2) {
          exec(addPet(ownerIdKey, petIdKey, petNameKey))
        },
        10d -> repeat(3) {
          exec(addPet(ownerIdKey, petIdKey, petNameKey))
        }
      )
    }

    setUp(scn.inject(constantUsersPerSec(20) during (60 seconds)).protocols(httpProtocol))
  //  setUp(scn.inject(rampUsersPerSec(5) to (20)  during(60 seconds)).protocols(httpProtocol))

  // generate an open workload injection profile
  // with levels of 10, 15, 20, 25 and 30 arriving users per second
  // each level lasting 10 seconds
  // separated by linear ramps lasting 10 seconds
  //  setUp(scn.inject(incrementUsersPerSec(5)
  //    .times(5)
  //    .eachLevelLasting(20 seconds)
  //    .separatedByRampsLasting(10 seconds)
  //    .startingFrom(10)).protocols(httpProtocol))

  //  setUp(scn.inject(constantUsersPerSec(100) during (30 minutes)).throttle(
  //    reachRps(20) in (10 seconds),
  //    holdFor(20 seconds),
  //    jumpToRps(50),
  //    holdFor(20 seconds)
  //  ).protocols(httpProtocol)).maxDuration(1 minute)

//  setUp(scn.inject(constantUsersPerSec(20) during (60 seconds)).protocols(httpProtocol)
//    .disablePauses)
//    .assertions(
//      global.responseTime.max.lt(50),
//      global.failedRequests.percent.is(0)
//    )
//    .maxDuration(30 seconds)


}
