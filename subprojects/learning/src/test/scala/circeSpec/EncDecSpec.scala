package circeSpec

import io.circe.Json
import org.scalatest.FunSuite
import io.circe.syntax._

class EncDecSpec extends FunSuite {

  test("Encode"){
    val intsJson: Json = List(1, 2, 3).asJson

    assert("""[1,2,3]""" == intsJson.noSpaces)
  }

  test("decode"){
    import io.circe.parser.decode

    val dec = decode[List[Int]]("[1, 2, 3]")

    assert(Right(List(1, 2, 3)) == dec)
  }


}
