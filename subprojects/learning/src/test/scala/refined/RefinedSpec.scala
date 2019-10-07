package learning.test.refined

import eu.timepit.refined.auto._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Positive

import org.scalatest.FunSuite

class RefinedSpec extends FunSuite {

  test("Can create person") {
    type Age =  Int Refined Positive

    case class Person(age: Age)

    Person(33)
  }
}
