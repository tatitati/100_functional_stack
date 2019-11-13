package shapeless

import org.scalatest.FunSuite
import shapeless.Generic

class ShapelessSpec extends FunSuite {
  case class IceCream(name: String, numCherries: Int, inCone: Boolean)
  val genericIceCream: Generic.Aux[IceCream, String :: Int :: Boolean :: HNil] = Generic[IceCream]

  test("Product(case class) -> Generid Product"){
    val givenIceCream = IceCream("vanilla", 1, false)
    val genRepr = genericIceCream.to(givenIceCream)
    
    assert("vanilla" :: 1 :: false :: HNil === genRepr)
  }

  test("Product(case class) <- Generid Product"){
    val givenGenRepr = "vanilla" :: 1 :: false :: HNil
    val iceCream = genericIceCream.from(givenGenRepr)

    assert(IceCream("vanilla",1,false) === iceCream)
  }
}
