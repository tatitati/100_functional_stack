package com.example.server_functional

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]) =
    Server_functionalServer.stream[IO].compile.drain.as(ExitCode.Success)
}