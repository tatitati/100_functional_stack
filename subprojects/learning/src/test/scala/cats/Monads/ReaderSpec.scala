package learning.test.cats.Monads

import cats.data.Reader
import org.scalatest.FunSuite

class ReaderSpec extends FunSuite {

    test("Example of Dependency injection") {
        case class User(name: String)
        case class Account(identifier: Int)
        case class DbConnection(ip: String)

        def createUserReader(u: User): Reader[DbConnection, Int] = Reader { _ => 0}
        def createAccountReader(a: Account): Reader[DbConnection, Int] = Reader { _ => 1}
        def registerNewUserReader(name: String): Reader[DbConnection, Int] = {
          createUserReader(User(name)).flatMap { id =>
            createAccountReader(Account(id))
          }
        }

        val register: Reader[DbConnection, Int] = registerNewUserReader("John")
        val accountId = register(DbConnection("122.33.222.11"))

        assert(1 === accountId)
    }
}
