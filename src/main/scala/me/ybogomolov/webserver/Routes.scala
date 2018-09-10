package me.ybogomolov.webserver

import cats.implicits._
import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.Router

import scala.concurrent.ExecutionContext.Implicits.global

object Routes {
  implicit val cs: ContextShift[IO] = IO.contextShift(global)

  def priceService[F[_]](repo: PriceRepository[F])(implicit F: Effect[F]) =
    HttpRoutes
      .of[F] {
        case GET -> Root / "prices" / id =>
          repo.findBy(id).map {
            case Some(price: Price) => Response(Status.Ok).withEntity(s"Price: $id, value: ${price.price}")
            case None               => Response(Status.NotFound)
          }
        case POST -> Root / "prices" => F.pure(Response(Status.Ok).withEntity("Initializing price parsing"))
      }

  def router[F[_]](repo: PriceRepository[F])(implicit F: Effect[F]) =
    Router("/" -> Routes.priceService[F](repo)).orNotFound
}
