package info.ludwikowski.vet

import info.ludwikowski.base.GatlingElUtils.toEL
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object VetActions {
  def createVet(firstNameKey: String, lastNameKey: String, specialityIdKey: String, specialityNameKey: String) = http("create vet")
    .post("/api/vets")
    .body(StringBody(
      s"""
         |{
         |  "id":null,
         |  "firstName":"${toEL(firstNameKey)}",
         |  "lastName":"${toEL(lastNameKey)}",
         |  "specialties":[
         |    {
         |      "id":${toEL(specialityIdKey)},
         |      "name":"${toEL(specialityNameKey)}"
         |    }
         |  ]
         |}
         |""".stripMargin)).asJson
    .check(status.is(201))

}
