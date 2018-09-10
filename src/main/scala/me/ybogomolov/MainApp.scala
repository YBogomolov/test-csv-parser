package me.ybogomolov

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import me.ybogomolov.webserver.{Price, PriceRepository, Routes}
import org.http4s.server.blaze.BlazeServerBuilder

object MainApp extends IOApp {
  val repo: PriceRepository[IO] = (id: String) => IO.pure(Some(Price(id, 42.0)))

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(8000)
      .withHttpApp(Routes.router(repo))
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
