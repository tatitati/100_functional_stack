package learning.test.cats

import org.scalatest.FunSuite
import cats.Functor
import cats.instances.list._
import cats.instances.option._

class CatFunctorSpec extends FunSuite {

//  trait Functor[F[_]] {
//    def map[X, Y](a: F[X])(f: X => Y): F[Y]
//  }

  test("functor list in cats") {
    val list1 = List(1,2,3)

    val list2 = Functor[List].map(list1)(_ * 2)

    assert(List(2,4,6) === list2)
  }

  test("functor option in cats") {
    val opt1 = Option(1,2,3)

    val opt2 = Functor[Option].map(opt1)(_.toString)

    assert(Some("(1,2,3)") === opt2)
  }
}
