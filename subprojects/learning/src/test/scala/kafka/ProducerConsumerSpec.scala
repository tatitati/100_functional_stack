package kafka

import java.time.Duration
import java.util.Properties
import java.util.concurrent.Future
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.scalatest.FunSuite
import java.time.Duration
import org.scalatest.FunSuite
import java.util
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.apache.kafka.clients.producer._

class ProducerConsumerSpec extends FunSuite {
  val topic = "mytopic"

  def getProducer(): KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }

  def send(msg: String, topic: String): Unit = {
    val producer = getProducer()
    val record = new ProducerRecord[String, String](topic, "key", msg)
    val result: Future[RecordMetadata] = producer.send(record)

    println("\n\n\n\n\n")
    val response: RecordMetadata = result.get
    println(response)
    println("\n\n\n\n\n")

    producer.close()
  }


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

  test("producer-consumer"){
    //producer
    val mymessage = "This is a message sent to kafka"
    send(mymessage.stripMargin, topic)

    // consumer
    val records = receive(topic)
    for(record <- records) {
      println("\n\n\n\n")
      println(record)
      println("\n\n\n\n")
    }
  }


}
