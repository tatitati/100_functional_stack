package kafka

import java.net.InetSocketAddress

import org.apache.zookeeper.server.{ServerCnxnFactory, ZooKeeperServer}
import org.scalatest.FunSuite

import scala.reflect.io.Directory

class ZookeperStartSpec extends FunSuite {
  def startZooKeeper(zooKeeperPort: Int, zkLogsDir: Directory): ServerCnxnFactory = {
    val tickTime = 2000

    val zkServer = new ZooKeeperServer(zkLogsDir.toFile.jfile, zkLogsDir.toFile.jfile, tickTime)

    val factory = ServerCnxnFactory.createFactory
    factory.configure(new InetSocketAddress("0.0.0.0", zooKeeperPort), 1024)
    factory.startup(zkServer)

    factory
  }

  test("asdfad") {
    val result = startZooKeeper(2181, Directory("/tmp"))
  }
}
