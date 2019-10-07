package learning.test.scala

import org.scalatest.FunSuite

class ForComprehesionSpec extends FunSuite{

  test("simple for compressions can be equivalent to map()") {
    val z = for {
      i <- List(1,2,3)
    } yield i * 2

    assert(Vector(2, 4, 6) === z)
    assert(Vector(2, 4, 6) === (1 to 3).map(_ * 2))
  }

  test("complex For comprehension can be translated to flatMap()") {
    val a = Some(2)
    val b = Some(3)
    val c = Some(4)

    val sum1 =
      a.flatMap { aVal =>
        b.flatMap { bVal =>
          c.map { cVal =>
            aVal + bVal + cVal
          }
        }
      }

    val sum2 = for {
      a <- Some(2)
      b <- Some(3)
      c <- Some(4)
    } yield(a + b + c)

    assert(Some(9) === sum2 && sum2 === sum1)
  }

  test("for compressions basic") {
    val result1 = for {
      x <- (1 to 3)
    } yield x

    val result2 = for {
      x <- 1 to 3
      y <- 4 to 5
    } yield(x, y)

    assert(List(1,2,3) === result1)
    assert(List((1,4), (1,5), (2,4), (2,5), (3,4), (3,5)) === result2)
  }

  test("how to do a+b ?") {
    val z = for {
      a <- Some(2)
      b <- Some(3)
    } yield a+b

    assert(Some(5) === z)
  }

  test("how to do a+b if some is not valid?") {
    val z = for {
      a <- Some(2)
      b <- None
      c <- Some(1)
    } yield {
      println("THIS TEXT IS NOT DISPLAYED")
      a+b+c
    }

    assert(None === z, "DOESNT MATTER AT ALL THE YIELD BLOCK, everything will be ignored (asserts, etc)")
  }

  test("more equivalents") {
    val w = for {
      xi <- 1 to 2
      yi <- 3 to 4
    } yield xi * yi

    val z = (1 to 2).flatMap {
      xi => (3 to 4).map { yi => xi * yi}
    }

    assert(Vector(3, 4, 6, 8) === w)
    assert(Vector(3, 4, 6, 8) === z)
  }
}
