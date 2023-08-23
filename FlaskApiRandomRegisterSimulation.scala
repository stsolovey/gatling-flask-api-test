package api_test_gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Random

class FlaskApiRandomRegisterSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://192.168.1.100:5000") 
    .header("Content-Type", "application/json")

  // Custom Feeder for random user registration data
  val randomUserFeeder = Iterator.continually(Map(
    "name" -> Random.alphanumeric.take(5).mkString,
    "email" -> s"${Random.alphanumeric.take(5).mkString}@example.com",
    "phone_number" -> s"+7(9${Random.nextInt(90) + 10})${Random.nextInt(900) + 100}-${Random.nextInt(90) + 10}-${Random.nextInt(90) + 10}",
    "plan_id" -> s"${Random.nextInt(5) + 1}",
    "password" -> Random.alphanumeric.take(8).mkString
  ))

  val scn = scenario("Test Flask API: Registration Random Users")
  .feed(randomUserFeeder)
  // Registration once
  .exec(http("Registration Request")
    .post("/register")
    .body(StringBody("""{
      "name": "#{name}",
      "email": "#{email}",
      "phone_number": "#{phone_number}",
      "plan_id": "#{plan_id}",
      "password": "#{password}"
    }""")).asJson
    .check(status.is(200))
  )

  
 setUp(
  scn.inject(
    //=====RampUp Load=====
      rampUsersPerSec(1) // Starting number of users per sec
      .to(500)           // Final number of users per sec
      .during(1.hour) // Duration
    

    // =====Stepping Load=====
    //  incrementUsersPerSec(15)
    //  .times(4)
    //  .eachLevelLasting(15.minutes)
    //  .startingFrom(15)

    //=====Stepping Load======
    //  constantUsersPerSec(5) during (20 minutes),
    //  constantUsersPerSec(30) during (20 minutes),
    //  constantUsersPerSec(60) during (20 minutes)

    //=====Stepping Load======
    //  incrementConcurrentUsers(50)
    //  .times(3)
    //  .eachLevelLasting(20.minutes)
    //  .startingFrom(1)

    //=====Spike Testing======
    //  atOnceUsers(3000) // Send 500 users at once

    //=====Constant Load======
    //  constantUsersPerSec(100).during(1.hour)


    //=====Ramp Down=======
    //  incrementConcurrentUsers(-300)
    //  .times(5)
    //  .eachLevelLasting(1.hour)
    //  .startingFrom(1505)

  )
).protocols(httpProtocol)

}
