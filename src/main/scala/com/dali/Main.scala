package com.dali

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.dali.user.api.UserRoute
import com.dali.user.application.{UserRepository, UserService}

import scala.io.StdIn

object Main extends App with UserRoute {

  implicit val system       = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher

  implicit lazy val ec = system.dispatchers.lookup("akka-http-mongo")

  val userDao = new UserRepository()

  override val userService = new UserService(userDao)

  val route = userRoute

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}
