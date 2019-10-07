package learning.test.scala

import org.scalatest.FunSuite
import scala.util.{Success, Try}

class LiftFunctionSpec extends FunSuite {

  test("We can lift our function with flatmaps/map") {
    def parseUserForm(age: String, numberOfBrothers: String): Try[String] = {
      val optAge: Try[Int] = Try{age.toInt}
      val optBrothers: Try[Int] = Try{numberOfBrothers.toInt}

      lifter(optAge, optBrothers)(insuranceRateQuote)
    }

    def insuranceRateQuote(age: Int, numberOfBrothers: Int): String = { // we lift this function to operate in a Try context
      s"Age: $age, Brothers: $numberOfBrothers"
    }

    def lifter[A, B, C](a: Try[A], b: Try[B])(f: (A, B) => C): Try[C] = {
      a flatMap (aa =>
        b map (bb =>
          f(aa, bb)
          )
        )
    }

    val result = parseUserForm("23", "3")
    assert(Success("Age: 23, Brothers: 3") === result)
  }

  test("We can lift our function with for{}") {
    def parseUserForm(age: String, numberOfBrothers: String): Try[String] = {
      val optAge: Try[Int] = Try{age.toInt}
      val optBrothers: Try[Int] = Try{numberOfBrothers.toInt}

      lifter(optAge, optBrothers)(insuranceRateQuote)
    }

    def insuranceRateQuote(age: Int, numberOfBrothers: Int): String = { // we lift this function to operate in a Try context
      s"Age: $age, Brothers: $numberOfBrothers"
    }

    def lifter[A, B, C](a: Try[A], b: Try[B])(f: (A, B) => C): Try[C] = {
      for {
        aa <- a
        bb <- b
      } yield f(aa, bb)
    }

    val result = parseUserForm("23", "3")
    assert(Success("Age: 23, Brothers: 3") === result)
  }
}
