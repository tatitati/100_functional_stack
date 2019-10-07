package learning.test.scala

import org.scalatest.FunSuite

class ApplySpec extends FunSuite{

  test("with case classes") {
    case class User(name: String)

    val a = User("Diego")
    assert(a.isInstanceOf[User])
  }

  test("with Companion object + apply()") {
    class User(val name: String)

    object User{
      def apply(withname: String): User = {
        new User(withname)
      }
    }

    // I can create the user in the same way that I was when using Case Class
    val a = User("Gonzalo")
    assert(a.isInstanceOf[User])
  }

  test("apply() can be used directly inside a normal sclass") {
    class User(val name: String) {
      def apply(title: String): String = {
        title + " " + name
      }
    }

    val a = new User("Gonzalo")
    assert("Mr Gonzalo" === a("Mr"))
  }
}
