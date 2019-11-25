package kafka

import java.time.Duration
import org.scalatest.FunSuite
import java.util
import java.util.Properties
import java.util.concurrent.Future
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer._

class ProducerSpec extends FunSuite {

  val topic = "mytopic"

  def getProducer(): KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }

  def send(msg: String, topic: String): Unit = {
    val record = new ProducerRecord[String, String](topic, "key", msg)
    val producer = getProducer()
    val result: Future[RecordMetadata] = producer.send(record)

    println("\n\n\n\n\n")
    val response: RecordMetadata = result.get
    println(response.offset())
    println("\n\n\n\n\n")
    producer.close()
  }

  test("producer"){
    val mymessage = "This is a message sent to kafka"

    send(mymessage.stripMargin, topic)
  }
}