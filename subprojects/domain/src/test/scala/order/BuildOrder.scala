package domain.test.order

import cats.effect.IO
import com.github.nscala_time.time.Imports.DateTime
import domain.order.{DEBITCARD, Order, OrderMedium}

object BuildOrder{
  def apply(): IO[BuildOrder] = {
    for {
      date <- IO(DateTime.now)
      medium <- IO(DEBITCARD)
      country <- IO("any_country")
    } yield BuildOrder(date, medium, country)
  }
}

final case class BuildOrder(date: DateTime, medium: OrderMedium, country: String) {
  def withCountry(withCountry: String): BuildOrder = {
    this.copy(country = withCountry)
  }

  def withDate(withDate: DateTime): BuildOrder = {
    this.copy(date = withDate)
  }

  def build(): Order = Order(date, medium, country)
}
