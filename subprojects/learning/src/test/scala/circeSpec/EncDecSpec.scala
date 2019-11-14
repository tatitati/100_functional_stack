package circeSpec

import io.circe.Json
import org.scalatest.FunSuite
import io.circe.syntax._

class EncDecSpec extends FunSuite {

  test("Encode"){
    val intsJson: Json = List(1, 2, 3).asJson
    //println(intsJson)
    //  [
    //    1,
    //    2,
    //    3
    //  ]

    val restored = intsJson.as[List[Int]]
    assert(Right(List(1, 2, 3)) == restored)
  }

  test("decode"){
    import io.circe.parser.decode

    val dec = decode[List[Int]]("[1, 2, 3]")
    assert(Right(List(1, 2, 3)) == dec)
  }


}
