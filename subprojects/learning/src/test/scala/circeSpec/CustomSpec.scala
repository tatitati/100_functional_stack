package circeSpec

import org.scalatest.FunSuite
import io.circe.{ Decoder, Encoder, HCursor, Json }
import io.circe.syntax._


class CustomSpec extends FunSuite {
  case class IceCream(name: String, size: Int)

  test("custom: encode"){
    implicit val encIcecream: Encoder[IceCream] = new Encoder[IceCream] {
      final def apply(ic: IceCream): Json = Json.obj(
        ("na_me", Json.fromString(ic.name)),
        ("si_ze", Json.fromInt(ic.size))
      )
    }

    val a = IceCream("vanilla", 18).asJson
    //println(a)
    //{
    //  "na_me" : "vanilla",
    //  "si_ze" : 18
    //}
  }

  test("custom: decode"){
    import io.circe.parser.decode

    implicit val decodeFoo: Decoder[IceCream] = new Decoder[IceCream] {
      final def apply(c: HCursor): Decoder.Result[IceCream] =
        for {
          foo <- c.downField("na_me").as[String]
          bar <- c.downField("si_ze").as[Int]
        } yield {
          IceCream(foo, bar)
        }
    }

    val a ="""
        |{
        |   "na_me" : "vanilla",
        |   "si_ze" : 18
        |}
        |""".stripMargin

    val dec = decode[IceCream](a)
    
    assert(Right(IceCream("vanilla",18)) == dec)
  }
}
