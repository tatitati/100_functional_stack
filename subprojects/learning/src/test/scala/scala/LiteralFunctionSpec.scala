package learning.test.scala

import org.scalatest.FunSuite

class LiteralFunctionSpec extends FunSuite {

  test("I can define a function with infered return type") {
    val func1 = (x: Int) => x * 2
    val func2 = (x: Int, y: Int) => x + y

    assert(6 === func1(3))
    assert(5 === func2(2, 3))
  }

  test("I can define a function with explicit return type") {
    val func1: Int => Double = (x: Int) => x * 2
    val func2: (Int , Int) => Int = (x, y) => { x + y }

    assert(6 === func1(3))
    assert(5 == func2(2, 3))
  }

  test("passing functions to functions") {
    def mathOperation(a: Int, b: Int, f: (Int, Int) => Int) = {
      f(a, b)
    }

    def sum(a: Int, b: Int): Int = {
      a + b
    }

    def multiply(a: Int, b: Int): Int = {
      a * b
    }

    assert(5 === mathOperation(2, 3, sum))
    assert(6 === mathOperation(2, 3, multiply))
    assert(6 === mathOperation(2, 3, (x: Int, y: Int) => x*y))
  }
}
