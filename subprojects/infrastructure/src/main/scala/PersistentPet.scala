package infrastructure

case class PersistentPet(
                          orderId: Option[Long],
                          name: String,
                          age: Int,
                          price: Int
)
