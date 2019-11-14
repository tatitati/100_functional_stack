package circeSpec

import io.circe._
import org.scalatest.FunSuite
import io.circe.syntax._

class SemiAutoSpec extends FunSuite {
  case class IceCream(name: String, size: Int)

  test("semi automatic: encode") {
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

  test("semi automatic: decode") {
    import io.circe.generic.semiauto._
    import io.circe.parser.decode

    implicit val decIcecream: Decoder[IceCream] = deriveDecoder[IceCream]
    implicit val encIcecream: Encoder[IceCream] = deriveEncoder[IceCream]

    val encoded = IceCream("vanilla", 15).asJson
    val dec = decode[IceCream](encoded.toString())

    assert(Right(IceCream("vanilla",15)) == dec)

  }

  test("automatic: encode"){
    import io.circe.generic.auto._

    val encoded = IceCream("vanilla", 15).asJson
    //println(encoded)
    //{
    //  "name" : "vanilla",
    //  "size" : 15
    //}
  }

  test("automatic: decode"){
    import io.circe.generic.auto._
    import io.circe.parser.decode

    val encoded = IceCream("vanilla", 15).asJson
    val dec = decode[IceCream](encoded.toString())

    assert(Right(IceCream("vanilla",15)) == dec)
  }
}
