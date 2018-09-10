package me.ybogomolov.webserver

trait PriceRepository[F[_]] {
  def findBy(id: String): F[Option[Price]]
}

final case class Price(id: String, price: Double) {}
