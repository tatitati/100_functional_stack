package learning.test.scala

import org.scalatest.FunSuite
import scala.collection.mutable.ArrayBuffer
import scala.util.{Success, Try}

class ForComprehesionSpec extends FunSuite{

  case class Person(name: String, age: Int)
  case class Animal(name: String, age: Int)

  test("simple for compressions can be equivalent to map()") {
    val z: List[Int] = for {
      i <- List(1,2,3)
    } yield i * 2

    assert(List(2, 4, 6) == z, "result with for")
    assert(List(2, 4, 6) === (1 to 3).map(_ * 2), "result using map is the same than the previous (with for)")
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

  test("None makes the for to quit") {
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

  test("Be careful with Strings, in fors they act like collection of chars"){

    def filterBySubstring(substring: String, words: List[String]): String = {
      words.filter(_.contains(substring)).head
    }

    val givenWords = List("exgirlfriend", "queue", "jmeter", "buff", "fuzzy", "coco", "wonderland", "butt", "and", "no", "none", "yes", "ever", "and", "bye", "whatever", "but", "where", "turkey", "lamp", "zapping")
    val givenSubstrings: List[String] = List("an", "er")

    val result1: List[String] = for{
      givenSubstring <- givenSubstrings // givenSubstring: String
      _ = println(givenSubstring)
      matchedWord = filterBySubstring(givenSubstring, givenWords) // matchedWord: String
    } yield matchedWord

    val result2: List[Char] = for{
      givenSubstring <- givenSubstrings // givenSubstring: String
      charsOfMatchedWord <- filterBySubstring(givenSubstring, givenWords) // charsOfMatchedWord: Char. Note: String is treated as a collection, so it provides Chars
    } yield charsOfMatchedWord

    assert(List("wonderland", "jmeter") == result1, "result1 failed")
    assert( List('w', 'o', 'n', 'd', 'e', 'r', 'l', 'a', 'n', 'd', 'j', 'm', 'e', 't', 'e', 'r') == result2, "result2 failed")
  }

  test("I can start with a List[Person] and finish with a List[String]"){
    val persons: List[Person] = List(Person("john", 23), Person("daniel", 45), Person("hulk", 102))

    val names: List[String] = for{
      person <- persons
    } yield person.name

    assert(List("john", "daniel", "hulk") == names)
  }

  test("The order of the loops matter. The resulting container type is the same as the first iterator"){
    val persons: List[Person] = List(Person("john", 23), Person("daniel", 45), Person("hulk", 102))
    val animals: ArrayBuffer[Animal] = ArrayBuffer(Animal("animal1", 23), Animal("animal2", 45))

    val names1: ArrayBuffer[String] = for{
      animal <- animals // animals: ArrayBuffer[Animal]
      person <- persons // List[Person]
    } yield person.name + animal.name

    val names2: List[String] = for{
      person <- persons //perso: List[Person]
      animal <- animals // ArrayBuffer[Animal]
    } yield person.name + animal.name

    assert(ArrayBuffer("johnanimal1", "danielanimal1", "hulkanimal1", "johnanimal2", "danielanimal2", "hulkanimal2") == names1)
    assert(List("johnanimal1", "johnanimal2", "danielanimal1", "danielanimal2", "hulkanimal1", "hulkanimal2") == names2)
  }

  test("container types must be the same") {
    val result: Option[Try[String]] = for {
      a <- Option("whatever")
      b <- Option(Try(a)) // Because I start with Option, I need options in the rest
    } yield(b)

    assert(Some(Success("whatever")) == result)
  }

  test("left side of for are pattern matthing") {
    val result1: Option[Person] = for{
      Person("aa", _) <- Option(Person("aa", 11))
      b <- Option(Person("bb", 22))
    } yield b

    val result2: Option[Person] = for{
      Person("cc", _) <- Option(Person("aa", 11)) // pattern matching fails here, so it returns Nones
      b <- Option(Person("bb", 22))
    } yield b

    assert(Some(Person("bb", 22)) == result1)
    assert(None == result2)
  }
}
