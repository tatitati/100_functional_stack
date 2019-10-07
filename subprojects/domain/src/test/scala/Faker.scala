package domain.test

import cats.data.State

final case class Seed(long: Long) {
  def next = Seed(long * 6364136223846793005L + 1442695040888963407L)
}

object Faker {
  def nextOf[T](items: T*): State[Seed, T] = {
     for {
        nextNumber <- nextInInterval(items.length)
     } yield items(nextNumber)

      // nextInInterval(items.length).map(nextNumber =>
      //   items(nextNumber)
      // )
  }

//  def nextString(length: Int = 10): State[Seed, String] = {
//    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
//    val inRange = chars.length
//
//    val total = List.range(0, length - 1)
//
//    val result2: List[State[Seed, String]] = for {
//      _ <- total
//    } yield nextInInterval(inRange).map { x =>
//      chars(x).toString
//    }
//
//    result2.foreach{ x =>
//      println(x)
//    }
//
//    result2
//  }

  def nextLong(): State[Seed, Long] = State(seed => (seed.next, seed.long))

  def nextBoolean(): State[Seed, Boolean] = State( seed => (seed.next, seed.long > 0))

  def nextInt(): State[Seed, Int] = State { seed => (seed.next, seed.long.toInt) }

  def nextIntPositive(): State[Seed, Int] = State { seed => (seed.next, seed.long.toInt.abs)}

  def nextInInterval(max: Int): State[Seed, Int] = State { seed =>
    val min = 0
    val number =  (seed.long.toInt.abs % (max - min)) + min

    (seed.next, number)
  }
}
