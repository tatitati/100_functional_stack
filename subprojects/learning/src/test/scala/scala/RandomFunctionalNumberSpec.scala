package learning.test.scala

import cats.data.State
import cats.effect.Sync
import org.scalatest.FunSuite

import scala.util.Random

class RandomFunctionalNumberSpec extends FunSuite {

  test("I can generate like:") {
    val number = Random.nextInt

  }

  test("But also I can generate like:") {
    val rng = new Random

    println(rng.nextInt())
    println(rng.nextInt())
  }

  test("without state monad") {
    case class SimpleRNG(seed: Long){
      def nextInt: (Int, SimpleRNG) = {
        val newSeed = (seed*0x5DEECE66DL + 0xBL) & ((1L << 48) - 1)
        val nextRandomInt = SimpleRNG(newSeed)
        val newState = (newSeed >>> 16).toInt
        (newState, nextRandomInt)
      }
    }

    val random1 = SimpleRNG(4)
    println(random1.nextInt)
    println(random1.nextInt)
    println(random1.nextInt)
  }

  test("with State monad (RECCOMMENDED)") {
    object RandomIntGenerator {
      case class Generator(seed: Long)

      def nextInt() = State[Generator, Int] {
        (init: Generator) =>
          val result: Long = (init.seed*0x5DEECE66DL + 0xBL) & ((1L << 48) - 1)
          val newState = Generator(result >>> 16)
          (newState, result.toInt)
      }
    }

    import RandomIntGenerator._

    val result1 = for{
      _ <- nextInt
      result <- nextInt
    } yield result

    assert(
      (Generator(4116161834L),-1523916761)
      == result1.run(Generator(120)).value)
  }
}
