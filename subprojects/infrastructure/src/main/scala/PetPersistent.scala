package infrastructure

case class PetPersistent(
                          orderId: Option[Long],
                          name: String,
                          age: Int,
                          price: Int
)
