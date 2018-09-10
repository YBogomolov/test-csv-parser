package me.ybogomolov.webserver

import cats.implicits._
import cats.effect._
import org.http4s._
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.dsl.io._
import org.http4s.server.Router
import org.http4s.Uri

import scala.concurrent.ExecutionContext.Implicits.global

object Routes {
  implicit val cs: ContextShift[IO] = IO.contextShift(global)

  val resourceUri: Uri = Uri.uri("http://localhost:8000/prices/1")

  def priceService[F[_]](repo: PriceRepository[F])(implicit F: ConcurrentEffect[F]) =
    HttpRoutes
      .of[F] {
        case GET -> Root / "prices" / id =>
          repo.findBy(id).map {
            case Some(price: Price) => Response(Status.Ok).withEntity(s"Price: $id, value: ${price.price}")
            case None               => Response(Status.NotFound)
          }
        case POST -> Root / "prices" =>
          BlazeClientBuilder[F](global).resource
            .use(_.expect[String](resourceUri).map(Response(Status.Ok).withEntity(_)))
      }

  def router[F[_]](repo: PriceRepository[F])(implicit F: ConcurrentEffect[F]) =
    Router("/" -> Routes.priceService[F](repo)).orNotFound
}
