package info.ludwikowski.owner

import io.gatling.core.Predef.{StringBody, jsonPath, _}
import io.gatling.http.Predef.{http, status, _}

object OwnerActions {

  val createOwner = http("create owner")
    .post("/api/owners")
    .body(StringBody(
      """
        |{
        |  "id":null,
        |  "firstName":"Andrzej",
        |  "lastName":"Ludwikowski",
        |  "address":"address",
        |  "city":"Wrocław",
        |  "telephone":"123123123"
        |}
        |""".stripMargin)).asJson
    .check(status.is(201))
    .check(jsonPath("$..id").exists.saveAs("ownerId"))

  val addPet = http("add pet for owner with id ${ownerId}")
    .post("/api/pets")
    .body(StringBody(
      """
        |{
        |  "id":null,
        |  "owner":{
        |    "id":${ownerId},
        |    "firstName":"Andrzej",
        |    "lastName":"Ludwikowski",
        |    "address":"address",
        |    "city":"Wrocław",
        |    "telephone":"123123123",
        |    "pets":[
        |
        |    ]
        |  },
        |  "name":"rex",
        |  "birthDate":"2019\/09\/05",
        |  "type":{
        |    "id":2,
        |    "name":"dog"
        |  }
        |}
        |""".stripMargin)).asJson
    .check(status.is(201))

}
