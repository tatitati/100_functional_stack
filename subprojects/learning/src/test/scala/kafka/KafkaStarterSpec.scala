package kafka

import java.util.Properties
import kafka.server.{KafkaConfig, KafkaServer}
import org.scalatest.FunSuite
import scala.reflect.io.Directory

class KafkaStarterSpec extends FunSuite {
  case class StreamConfig(streamTcpPort: Int = 9092,
                          streamStateTcpPort :Int = 2181,
                          stream: String,
                          numOfPartition: Int = 1,
                          nodes: Map[String, String] = Map.empty)

  def startKafkaBroker(config: StreamConfig, kafkaLogDir: Directory): KafkaServer = {
    val syncServiceAddress = s"localhost:${config.streamStateTcpPort}"

    val properties: Properties = new Properties
    properties.setProperty("zookeeper.connect", syncServiceAddress)
    properties.setProperty("broker.id", "0")
    properties.setProperty("host.name", "localhost")
    properties.setProperty("advertised.host.name", "localhost")
    properties.setProperty("port", config.streamTcpPort.toString)
    properties.setProperty("auto.create.topics.enable", "true")
    properties.setProperty("log.dir", kafkaLogDir.toAbsolute.path)
    properties.setProperty("log.flush.interval.messages", 1.toString)
    properties.setProperty("log.cleaner.dedupe.buffer.size", "1048577")

    config.nodes.foreach {
      case (key, value) => properties.setProperty(key, value)
    }

    val broker = new KafkaServer(new KafkaConfig(properties))
    broker.startup()

    println(s"KafkaStream Broker started at ${properties.get("host.name")}:${properties.get("port")} at ${kafkaLogDir.toFile}")
    broker
  }

    test(""){
      startKafkaBroker(
        StreamConfig(streamTcpPort = 9092, streamStateTcpPort = 2181, stream = "test-topic", numOfPartition = 1),
        Directory("/tmp")
      )
    }
}
