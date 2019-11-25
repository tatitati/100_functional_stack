package kafka

import java.time.Duration
import org.scalatest.FunSuite
import java.util
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.apache.kafka.clients.producer._


class ConsumerSpec extends FunSuite {

  val topic = "mytopic"

  def getConsumer(): KafkaConsumer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "consumer-group")
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    val topics = List(topic).asJava
    consumer.subscribe(topics)
    consumer
  }

  def receive(topic: String): Iterable[ConsumerRecord[String, String]] = {
    val consumer = getConsumer()
    val records = consumer.poll(Duration.ofMillis(1000)).asScala
    consumer.close()

    records
  }

  test("producer"){
    val records = receive(topic)
    for(record <- records) {
      println("\n\n\n\n")
      println(record)
      println("\n\n\n\n")
    }
  }

  //test("consumer"){
  //  println(receive("test").foreach(println))
  //}
  //
  //test("topics can be created on the fly"){
  //  Thread.sleep(2000)
  //  send(
  //    """
  //      |######################
  //      |######################
  //      |######################
  //      |######################
  //      |######################
  //      |""".stripMargin, "my_new_topic")
  //}
  //
  //test("topics can be created on the fly2"){
  //  println("\n\n\n\n=================")
  //  println(receive("my_new_topic").foreach(println))
  //  println("\n\n\n\n=================")
  //}
}