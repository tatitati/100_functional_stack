package learning.test.doobie

import org.scalatest.FunSuite
import doobie.implicits._
import shapeless._

class SelectingDataSpec extends FunSuite with CustomDbConnection {
  test("Can request to db") {
    val result: Int = sql"select 42"
      .query[Int]       // Query0[Int]
      .unique           // ConnectionIO[Int]
      .transact(xa)     // IO[Int]
      .unsafeRunSync()  // Int

    assert(42 == result)
  }

  test("Can fetch ALL the rows"){
    val result1: List[String] = sql"select name from country"
      .query[String]    // Query0[String]
      .to[List]         // ConnectionIO[List[String]]
      .transact(xa)     // IO[List[String]]
      .unsafeRunSync    // List[String]


    assert(List("spain", "italy", "France") == result1) // we read by default all the rows

    val result2 = result1.take(2)
    assert(List("spain", "italy") == result2)
  }

  test("Can avoid fetching all the rows with streams") {
    val result: List[String] = sql"select name from country"
      .query[String]    // Query0[String]
      .stream           // Stream[ConnectionIO, String]
      .take(2)          // Stream[ConnectionIO, String]
      .compile.toList   // ConnectionIO[List[String]]
      .transact(xa)     // IO[List[String]]
      .unsafeRunSync    // List[String]

    assert(List("spain", "italy") == result)
  }

  test("multi column select") {

    val result = sql"select code, name, population, gnp from country"
      .query[(String, String, Int)]
      .stream
      .take(2)
      .compile.toList
      .transact(xa)
      .unsafeRunSync

    assert(
      List(("SP","spain",500), ("IT","italy",600))
      == result
    )
  }

  test("Can specify in which format to return the results: in shapeless format") {
    val result = sql"select code, name, population, gnp from country"
      .query[String :: String :: Int :: Option[Double] :: HNil]
      .stream
      .take(5)
      .compile.toList
      .transact(xa)
      .unsafeRunSync

    assert(
      List(
        "SP" :: "spain" :: 500 :: Some(8.0) :: HNil,
        "IT" :: "italy" :: 600 :: Some(3.0) :: HNil,
        "FR" :: "France" :: 650 :: Some(7.0) :: HNil
      ) == result
    )
  }

  test("Can specify in which format to return the results: in case classes") {
    case class Country(code: String, name: String, pop: Int, gnp: Option[Double])

    val result = sql"select code, name, population, gnp from country"
      .query[Country]
      .stream
      .take(5)
      .compile.toList
      .transact(xa)
      .unsafeRunSync

    assert(
      List(
        Country("SP","spain",500,Some(8.0)),
        Country("IT","italy",600,Some(3.0)),
        Country("FR","France",650,Some(7.0))
      ) == result
    )
  }
}
