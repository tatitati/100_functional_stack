package shapelessSpecs

import org.scalatest.FunSuite
import shapeless._


class CoproductSpec extends FunSuite {
  sealed trait Shape
  final case class Rectangle(width: Double, height: Double) extends Shape
  final case class Circle(radios: Double) extends Shape


  val genericShape = Generic[Shape]

  test("test"){
    val genericCoproduct1 = genericShape.to(Rectangle(3.0, 4.0))
    val genericCoproduct2 = genericShape.to(Circle(3.0))

    assert(Inr(Inl(Rectangle(3.0,4.0))) == genericCoproduct1)
    assert(Inl(Circle(3.0)) == genericCoproduct2)
  }
}
