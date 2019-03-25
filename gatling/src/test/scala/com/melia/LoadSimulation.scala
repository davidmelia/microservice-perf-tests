package com.melia

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

class LoadSimulation extends Simulation {

  var targetUrl = System.getProperty("TARGET_URL")
  
  val simUsers = System.getProperty("SIM_USERS").toInt

  val myScenario = scenario("Gatling Perf Test").exec(
    repeat(30) {
      exec(
        http("request_1").get(targetUrl)
      ).pause(1 second, 2 seconds)
    }
  ) 

  
  setUp(myScenario.inject(rampUsers(simUsers).during(30 seconds)))
}
 