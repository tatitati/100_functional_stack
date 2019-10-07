package learning.test.cats

import cats.Show
import cats.syntax.show._
import org.scalatest.FunSuite
import learning.test.fpModules._

class CatsTypeClassesSpec extends FunSuite {
  sealed trait Topping
  case object Cheese extends Topping
  case object Pepperoni extends Topping
  case object Sausage extends Topping
  case object Mushrooms extends Topping
  case object Onions extends Topping


  implicit val pizzaShow = Show.show[Pizza] { p =>
    s"""|Pizza(${p.crustSize}, ${p.crustType}),
        |     toppings = ${p.toppings}""".stripMargin
  }

  test("asdfasd") {
    val p = Pizza(SmallCrustSize, RegularCrustType, Seq())
    println(p.show)
  }
}
