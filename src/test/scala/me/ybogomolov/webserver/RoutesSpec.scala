package me.ybogomolov.webserver

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._
import org.scalatest.FunSpec

class RoutesSpec extends FunSpec {
  describe("Routes spec") {
    it("should respond to id request with success") {
      val repo: PriceRepository[IO] = (id: String) => IO.pure(Some(Price(id, 42.0)))

      val routes   = Routes.priceService(repo)
      val response = routes.orNotFound.run(Request(Method.GET, Uri.uri("/prices/42"))).unsafeRunSync()

      assert(response.status == Status.Ok)
      assert(response.as[String].unsafeRunSync == "Price: 42, value: 42.0")
    }

    it("should respond to id request with failure") {
      val repo: PriceRepository[IO] = (_: String) => IO.pure(None)

      val routes   = Routes.priceService(repo)
      val response = routes.orNotFound.run(Request(Method.GET, Uri.uri("/prices/42"))).unsafeRunSync()

      assert(response.status == Status.NotFound)
    }

    it("should respond to POST request with message") {
      val repo: PriceRepository[IO] = (_: String) => IO.pure(None)

      val routes   = Routes.priceService(repo)
      val response = routes.orNotFound.run(Request(Method.POST, Uri.uri("/prices"))).unsafeRunSync()

      assert(response.status == Status.Ok)
      assert(response.as[String].unsafeRunSync == "Initializing price parsing")
    }
  }
}
