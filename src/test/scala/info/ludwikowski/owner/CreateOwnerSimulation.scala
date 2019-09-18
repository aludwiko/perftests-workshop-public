package info.ludwikowski.owner

import info.ludwikowski.base.PetClinicSimulation
import io.gatling.commons.validation.{Failure, Success, Validation}
import io.gatling.core.Predef._
import io.gatling.core.check.Validator
import io.gatling.http.Predef._

class CreateOwnerSimulation extends PetClinicSimulation {

  val scn = scenario("Exercise 2")
    .exec(
      http("create owner")
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
        .check(header("Location").exists)
        //{"id":24,"firstName":"123","lastName":"123","address":"123","city":"123","telephone":"123123123","pets":[]}
        //.check(bodyString.is("{full json response}")
        .check(substring("id").count.is(1))
        .check(regex(".*firstName\":\"(.*?)\"").is("Andrzej"))
        .check(regex(".*firstName\":\"(.*)\",\"lastName\":\"(.*?)\"").ofType[(String, String)].is(("Andrzej", "Ludwikowski")))
        .check(jsonPath("$..city").is("Wrocław"))
        .check(jsonPath("$..city").transform(_.toLowerCase).is("wrocław"))
        .check(jsonPath("$..telephone").validate(digitsOnly))
    )

  setUp(scn.inject(atOnceUsers(5)).protocols(httpProtocol))
}

object digitsOnly extends Validator[String]{

  override def name: String = "digits only"

  override def apply(actual: Option[String], displayActualValue: Boolean): Validation[Option[String]] = {
    actual match {
      case Some(value) =>
        if (value.matches("[0-9]+")) {
          Success(actual)
        }else{
          Failure(s"value $value didn't contain only digits")
        }
      case None => Validator.FoundNothingFailure
    }
  }
}
