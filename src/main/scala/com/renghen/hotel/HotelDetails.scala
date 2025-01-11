package com.renghen.hotel

import com.renghen.common.Address
import scala.collection.mutable.HashMap

enum RoomStatus:
  case Available, Booked, Occupied

end RoomStatus

final case class Room(
    roomType: Int,
    status: RoomStatus)

final case class HotelDetails(
    name: String,
    address: Address,    
    rooms: HashMap[Int,Room])
