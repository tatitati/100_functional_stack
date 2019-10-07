package domain.order

import com.github.nscala_time.time.Imports._

final case class Order(date: DateTime, medium: OrderMedium, country: String) {

  def setMedium(withmedium: OrderMedium): Order = {
    this.copy(medium = withmedium)
  }

  def setCountry(withCountry: String): Either[OrderError, Order] = {
    withCountry match {
      case "" => Left(OrderErrorCountry)
      case _ => Right(this.copy(country = withCountry))
    }

  }
}
