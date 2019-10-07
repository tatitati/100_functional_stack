package learning.test.doobie

import org.scalatest.FunSuite
import doobie.implicits._

class ParameterizeQueriesSpec extends FunSuite with CustomDbConnection {

  test("Can add some dynamic parameters to a query") {
    case class Country(code: String, name: String, pop: Int, gnp: Option[Double])

    def biggerThan(minPop: Int) = sql"""
      select code, name, population, gnp
      from country
      where population > $minPop
      """.query[Country]

    val result = biggerThan(600)
      .to[List]
      .transact(xa)
      .unsafeRunSync

    assert(
      List(Country("FR","France",650,Some(7.0)))
      == result
    )

  }

}
