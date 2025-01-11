package com.renghen.hotel

import com.renghen.common.Address

enum RoomStatus:
  case Available, Booked, Occupied

end RoomStatus

final case class Room(
    number: String,
    roomType: String,
    status: RoomStatus)

final case class HotelDetails(
    name: String,
    address: Address,
    rooms: Room)
