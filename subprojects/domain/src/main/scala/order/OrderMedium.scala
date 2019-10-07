package domain.order

sealed trait OrderMedium
final case object DEBITCARD extends OrderMedium
final case object CREDITCARD extends OrderMedium
final case object PAYPAL extends OrderMedium
