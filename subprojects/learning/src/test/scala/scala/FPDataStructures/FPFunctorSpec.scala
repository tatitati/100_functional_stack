package learning.test.scala.FPDataStructures

import org.scalatest.FunSuite

class FPFunctorSpec extends FunSuite {

  // definition
  trait Functor[F[_]] {
    def map[X, Y](a: F[X])(f: X => Y): F[Y]
  }

  object functor {
    val listFunctor = new Functor[List] {
      def map[X, Y](a: List[X])(f: X => Y): List[Y] = a.map(f)
    }
  }

  test("functor") {
    val listFunctor = functor.listFunctor
    val result = listFunctor.map(List(1,2,3,4,5,6))(_ * 2)

    assert(result === List(2,4,6,8,10,12))
  }
}
