package concurrency

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
import scala.collection.parallel.immutable.ParVector

class ParallelCollectionsSpec extends FunSuite {
  test("parallel collections dont follow any order"){
    val numbers = List(1, 2, 3, 4, 5, 6, 7, 8)

    //    val result = numbers.par.foreach{ println }
    //    3
    //    2
    //    4
    //    1
    //    5
    //    6
    //    7
    //    8
    //    ()
  }

  test("par.map is the parallel version of map") {
    val numbers = List(1, 2, 3, 4, 5, 6, 7, 8)
    val result = numbers.map(_.toString)
    assert(List("1", "2", "3", "4", "5", "6", "7", "8") == result)
  }

  test("par.map dfasdfasdf") {
    val numbers = List(1, 2, 3, 4, 5, 6, 7, 8)
    val result = numbers.par.map{ x =>
      // println(Thread.currentThread().getName() + " is picking up the number: " + x)
      x * 10
    }
    assert(ParVector(10, 20, 30, 40, 50, 60, 70, 80) == result)
  }
}
