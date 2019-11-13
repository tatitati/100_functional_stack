package kafka

import org.scalatest.FunSuite
import java.util
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer._

class ProducerSpec extends FunSuite {

  def send(msg: String, topic: String): Unit = {
    def getProducer(): KafkaProducer[String, String] = {
      val props = new Properties()
      props.put("bootstrap.servers", "localhost:9092")
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      new KafkaProducer[String, String](props)
    }

    val record = new ProducerRecord[String, String](topic, "key", msg)
    val producer = getProducer()
    producer.send(record)
    producer.close()
  }

  def receive(topic: String): Iterator[String] = {
    def getConsumer(): KafkaConsumer[String, String] = {
      val props = new Properties()
      props.put("bootstrap.servers", "localhost:9092")
      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      props.put("auto.offset.reset", "latest")
      props.put("group.id", "consumer-group")
      val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
      consumer.subscribe(util.Arrays.asList("test"))
      consumer
    }

    val consumer = getConsumer()
    val record = consumer.poll(1000).asScala
    val datareceived  = for {
      data <- record.iterator
    } yield data.value()
    consumer.close()

    datareceived
  }

  test("producer"){
    send(
      """
        |@@@@@@@@@@@@@@@@@@@@@@
        |@@@@@@@@@@@@@@@@@@@@@@
        |@@@@@@@@@@@@@@@@@@@@@@
        |@@@@@@@@@@@@@@@@@@@@@@
        |""".stripMargin, "test")
  }

  test("consumer"){
    println(receive("test").foreach(println))
  }

  test("topics can be created on the fly"){
    Thread.sleep(2000)
    send(
      """
        |######################
        |######################
        |######################
        |######################
        |######################
        |""".stripMargin, "my_new_topic")
  }

  test("topics can be created on the fly2"){
    println("\n\n\n\n=================")
    println(receive("my_new_topic").foreach(println))
    println("\n\n\n\n=================")
  }
}