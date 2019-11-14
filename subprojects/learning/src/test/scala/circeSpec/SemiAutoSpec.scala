package circeSpec

import io.circe._
import org.scalatest.FunSuite
import io.circe.syntax._

class SemiAutoSpec extends FunSuite {
  case class IceCream(name: String, size: Int)

  test("semi automatic") {
    import io.circe.generic.semiauto._

    implicit val decIcecream: Decoder[IceCream] = deriveDecoder[IceCream]
    implicit val encIcecream: Encoder[IceCream] = deriveEncoder[IceCream]

    val encoded = IceCream("vanilla", 15).asJson

    //println(encoded)
    //{
    //  "name" : "vanilla",
    //  "size" : 15
    //}

  }

  test("automatic"){
    import io.circe.generic.auto._

    val encoded = IceCream("vanilla", 15).asJson

    //println(encoded)
    //{
    //  "name" : "vanilla",
    //  "size" : 15
    //}
  }
}
