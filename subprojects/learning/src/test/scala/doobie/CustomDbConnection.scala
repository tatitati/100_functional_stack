package learning.test.doobie

import cats.effect.{Blocker, IO}
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import org.scalatest.FunSuite

trait CustomDbConnection extends FunSuite {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql:doobie_db",
    "postgres",
    "1234",
    Blocker.liftExecutionContext(ExecutionContexts.synchronous)
  )

  // these are to get .quick()
  val y = xa.yolo
  import y._
}
