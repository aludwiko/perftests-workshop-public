package info.ludwikowski.jms

import io.gatling.core.Predef._
import io.gatling.jms.Predef._
import javax.jms._
import org.apache.activemq.ActiveMQConnectionFactory

import scala.concurrent.duration._
import scala.util.Random

class SimpleJmsSimulation extends Simulation {

  val connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616")

  val jmsConfig = jms
    .connectionFactory(connectionFactory)
    .usePersistentDeliveryMode


  val randomMessage = Iterator.continually({
    Map(
      "message" -> s""""hello_${Random.alphanumeric.take(4).mkString}"""" //" required
    )
  })

  val scn = scenario("JMS DSL test")
    .feed(randomMessage)
    .exec(jms("req reply testing").send
      .queue("inbox")
      .textMessage("${message}")
      .property("_type", "java.lang.String"))


  setUp(scn.inject(constantUsersPerSec(5).during(30 seconds)))
    .protocols(jmsConfig)

  def checkBodyTextCorrect(m: Message) = {
    m match {
      case tm: TextMessage => tm.getText == tm.getText.toUpperCase
      case _ => false
    }
  }
}
