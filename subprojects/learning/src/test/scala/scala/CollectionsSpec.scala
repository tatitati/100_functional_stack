package learning.test.scala

import org.scalatest.FunSuite
import scala.collection.mutable.ArrayBuffer

class CollectionsSpec extends FunSuite {

  test("Collection, when compared, only compare the order of the items and the items, but no the type container") {
    assert(List(2, 4, 6) == ArrayBuffer(2, 4, 6))
    assert(ArrayBuffer(2, 4, 6) == Vector(2, 4, 6))
  }
}
