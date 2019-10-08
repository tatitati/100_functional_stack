package domain.pet

final case class Pet(
                      surrogateId: Option[Long],
                      name: String,
                      age: Int,
                      price: Int
)
