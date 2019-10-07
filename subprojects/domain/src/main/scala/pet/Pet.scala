package domain.pet

import domain.order.OrderId

final case class Pet(
                orderId: OrderId,
                name: String,
                age: Int,
                price: Int
)
