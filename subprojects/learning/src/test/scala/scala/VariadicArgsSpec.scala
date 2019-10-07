package learning.test.scala

import org.scalatest.FunSuite

class VariadicArgsSpec extends FunSuite {

  test("can pass a variant amount of arguments"){

    def printAllNames(names: String*): String = {
      var result = ""
      names.foreach{result += _}
      result
    }

    assert("aaabbbb" === printAllNames("aaa", "bbbb"))
  }
}
