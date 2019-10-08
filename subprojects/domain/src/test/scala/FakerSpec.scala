package domain.test

import cats.data.State
import domain.order.OrderId
import domain.test.pet.BuilderPetOps.BuilderState
import org.scalatest.FunSuite

class FakerSpec extends FunSuite {

  test("Faker.nextLong()") {
    val tuple: State[Seed, (Long, Long, Long)] = for{
      long1 <- Faker.nextLong()
      long2 <- Faker.nextLong()
      long3 <- Faker.nextLong()
    } yield (long1, long2, long3)

    val (state, value) = tuple.run(Seed(2323)).value
    assert(value == (2323,-8957604078071282010L,3854786773771191933L), "long1 and long2 must be differents")
  }

  test("Faker.nextBoolean()") {
    val tuple: State[Seed, (Boolean, Boolean, Boolean)] = for{
      bool1 <- Faker.nextBoolean()
      bool2 <- Faker.nextBoolean()
      bool3 <- Faker.nextBoolean()
    } yield (bool1, bool2, bool3)

    val (state, value) = tuple.run(Seed(2323)).value

    assert(value == (true,false,true))
  }

  test("Faker.nextIntPositive()") {
    val tuple: State[Seed, (Int, Int, Int)] = for{
      int1 <- Faker.nextIntPositive()
      int2 <- Faker.nextIntPositive()
      int3 <- Faker.nextIntPositive()
    } yield (int1, int2, int3)

    val (state, value) = tuple.run(Seed(2323)).value

    assert(value == (2323,403143002,1350465923))
  }

  test("Faker.nextOf()") {
    val tuple: State[Seed, (String, String, String)] = for{
      string1 <- Faker.nextOf("a", "b")
      string2 <- Faker.nextOf("a", "b")
      string3 <- Faker.nextOf("a", "b")
    } yield (string1, string2, string3)

    val (state, value) = tuple.run(Seed(2323)).value

    assert(value == ("b","a","b"))
  }

  test("can create random text") {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val inRange = chars.length

    val total = List.range(0, 10 - 1)

    val result2: List[State[Seed, String]] = for {
      _ <- total
    } yield Faker.nextInInterval(inRange).map { x =>
      chars(x).toString
    }

    val resultFinal = result2(0).run(Seed(2323)).value
    println(resultFinal)
  }

//  test("can generate random strings") {
//    val tuple: State[Seed, (String, String, String)] = for{
//      string1 <- Faker.nextString()
//      string2 <- Faker.nextString()
//      string3 <- Faker.nextString()
//    } yield (string1, string2, string3)
//
//    val (state, value) = tuple.run(Seed(2323)).value
//
//    assert(value == ("b","a","b"))
//  }


  test("Understand a bit better State") {
    val nextIntPositive: State[Seed, Int] = Faker.nextIntPositive()

    val createPet: State[Seed, BuilderState] = for {
      age <- nextIntPositive
      price <- nextIntPositive
    } yield BuilderState(None, age, "any name", price)

    assert(
      (Seed(3854786773771191933L), BuilderState(None,542183646,"any name",584218809))
        === createPet.run(Seed(2323)).value
    )
  }

  test("State is passed even if we dont care result values") {
    val nextIntPositive: State[Seed, Int] = Faker.nextIntPositive()

    val createPet: State[Seed, Unit] = for {
      _ <- nextIntPositive
      _ <- nextIntPositive
    } yield Unit

    assert(
      (Seed(3854786773771191933L), ())
        === createPet.run(Seed(2323)).value
    )
  }
}
