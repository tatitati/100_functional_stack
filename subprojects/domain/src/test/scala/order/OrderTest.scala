package domain.test.order

import org.scalatest.FunSuite
import com.github.nscala_time.time.Imports._
import cats.effect.IO
import domain.order.{DEBITCARD, Order, OrderError}

class OrderTest extends FunSuite {

  test("Builder") {
    val buildOrder: IO[Order] = for {
      any <- BuildOrder()
      onCountry <- IO{ any.withCountry("any_contry") }
      order <- IO{onCountry.build()}
    } yield order

    val anOrder = buildOrder.unsafeRunSync()

    assert(anOrder.isInstanceOf[Order])
  }

  test("Can create an order in a functional way"){
    val build: IO[Order] = for {
      date <- IO{ DateTime.now }
      medium <- IO(DEBITCARD)
      country <- IO("france")
    } yield Order(date, medium, country)

    val anOrder = build.unsafeRunSync()

    assert(anOrder.isInstanceOf[Order])
  }

  test("Country can be updated") {
    val buildOrder: IO[Order] = for {
      any <- BuildOrder()
      order <- IO{any.build()}
    } yield order

    val anOrder = buildOrder.unsafeRunSync()
    val updatedOrder = anOrder.setCountry("any_country")

    assert(updatedOrder.isInstanceOf[Right[_, Order]])
  }

  test("Country can return LEFT") {
    val buildOrder: IO[Order] = for {
      any <- BuildOrder()
      order <- IO{any.build()}
    } yield order

    val anOrder = buildOrder.unsafeRunSync()
    val updatedOrder = anOrder.setCountry("")

    assert(updatedOrder.isInstanceOf[Left[OrderError, _]])
  }
}
