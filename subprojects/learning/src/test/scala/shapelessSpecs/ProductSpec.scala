package shapelessSpecs

import org.scalatest.FunSuite
import shapeless._

class ProductSpec extends FunSuite {
  case class IceCream(name: String, numCherries: Int, inCone: Boolean)
  val genericIceCream = Generic[IceCream]

  test("Product(case class) -> Generid Product"){
    val givenIceCream = IceCream("vanilla", 1, false)
    val genericProduct = genericIceCream.to(givenIceCream)

    assert("vanilla" :: 1 :: false :: HNil === genericProduct)
  }

  test("Product(case class) <- Generid Product"){
    val givenGenProduct = "vanilla" :: 1 :: false :: HNil
    val iceCream = genericIceCream.from(givenGenProduct)

    assert(IceCream("vanilla",1,false) === iceCream)
  }
}
