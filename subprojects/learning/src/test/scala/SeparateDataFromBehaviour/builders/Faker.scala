package SeparateDataFromBehaviour.builders

import com.github.nscala_time.time.Imports.DateTime
import scala.util.Random

object Faker {
  def anyOf[T](items: T*): T = items(Random.nextInt(items.length))

  def text(length: Int = 10): String = {
    val value = for(i <- 1 to length) yield { Random.nextPrintableChar() }
    value.mkString
  }

  def boolean: Boolean = Random.nextBoolean()

  def int: Int = Random.nextInt()

  def long: Long = Random.nextLong()

  def date: DateTime = {
    anyOf(
      DateTime.now().minusYears(anyOf(1, 2, 3)),
      DateTime.now(),
      DateTime.now().plusYears(anyOf(1, 2, 3))
    )
  }
}