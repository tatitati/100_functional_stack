package learning.test.scala

import org.scalatest.FunSuite

class ClassSpec extends FunSuite {
  class Something(name: String) {

    def getName(): String = {
      name
    }
  }

  test("class constructor directly assign variables to the class") {
    var a = new Something("whatever")
    assert("whatever" === a.getName())
  }
}
