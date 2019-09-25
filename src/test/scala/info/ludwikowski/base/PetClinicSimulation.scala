package info.ludwikowski.base

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef.{Simulation, _}
import io.gatling.http.Predef.http

class PetClinicSimulation extends Simulation {

  private val conf: Config = ConfigFactory.load

  private val baseUrl = conf.getString("simulation.url")

  val httpProtocol = http
    .baseUrl(baseUrl) // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

}
