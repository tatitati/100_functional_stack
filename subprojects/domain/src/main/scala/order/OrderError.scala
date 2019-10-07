package domain.order

sealed trait OrderError
final case object OrderErrorCountry extends OrderError

