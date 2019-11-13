package circeSpec

import org.scalatest.FunSuite
import io.circe._, io.circe.parser._

class ParseSpec extends FunSuite {

  test("parse proper json"){
    val rawJson: String = """
        {
          "foo": "bar",
          "baz": 123,
          "list of stuff": [ 4, 5, 6 ]
        }
    """


    val parseResult: Either[ParsingFailure, Json] = parse(rawJson) // Right("""{"foo" : "bar","baz" : 123,"list of stuff" : [4, 5, 6]}""")
  }

  test("parse wrong json"){
    val parseResult: Either[ParsingFailure, Json] = parse("this is wrong") // Left(io.circe.ParsingFailure: expected true got 'this i...'
  }
}
