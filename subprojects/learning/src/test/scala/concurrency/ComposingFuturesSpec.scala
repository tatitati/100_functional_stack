package concurrency

import org.scalatest.FunSuite

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

class ComposingFuturesSpec extends FunSuite {

  def fut1: Future[Int] = Future {
//    println(Thread.currentThread().getName())
    Thread.sleep(5000)
    5
  }

  def fut2(num: Int): Future[String] = Future {
//    println(Thread.currentThread().getName())
    Thread.sleep(5000)
    s"next number is " + (num+1)
  }

  test("I can map over a future") {
    val futureResult: Future[Int] = fut1.map{ num => num * 10}

    val value: Int = Await.result(futureResult, 15 seconds)

    assert(50 == value)
  }

  test("Chaining futures: map create nested Futures, sometimes we have to wait of each nested, one by one"){
    val futureText: Future[Future[String]] = fut1.map(fut2(_))

    val text1: Future[String] = Await.result(futureText, 15 seconds)
    val text2: String = Await.result(text1, 15 seconds)

    assert("next number is 6" == text2)
  }

  test("Chaining futures: With flatMap we have to wait only for a future, which is not nested"){
    val endValue: Future[String] = fut1.flatMap(fut2(_))

    val text: String = Await.result(endValue, 15 seconds)

    assert("next number is 6" == text)
  }
}
