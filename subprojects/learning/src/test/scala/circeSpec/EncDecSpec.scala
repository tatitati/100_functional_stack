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

  }
}
