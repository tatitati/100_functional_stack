package circeSpec

import org.scalatest.FunSuite
import cats.syntax.either._
import io.circe._, io.circe.parser._

class ExtractFromJsonSpec extends FunSuite {
  val json: String = """
  {
    "id": "c730433b-082c-4984-9d66-855c243266f0",
    "name": "Foo",
    "counts": [1, 2, 3],
    "values": {
      "bar": true,
      "baz": 100.001,
      "qux": ["a", "b"]
    }
  }
"""

  val doc: Json = parse(json).getOrElse(Json.Null)

  test("can extract fields"){
    val cursor: HCursor = doc.hcursor

    val baz: Decoder.Result[Double] = cursor.downField("values").downField("baz").as[Double]
    val secondQux: Decoder.Result[String] = cursor.downField("values").downField("qux").downArray.as[String]

    assert(Right(100.001) == baz)
    assert(Right("a") == secondQux)
  }
}
