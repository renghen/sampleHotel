package com.renghen.hotel

import com.renghen.common.Address
import com.renghen.customer.{Customer, CustomerDataResponse}
import com.renghen.customer.CustomerOpsError

import collection.mutable.HashMap
import java.time.LocalDate

enum RoomStatus:
  case Available, Booked, Occupied

end RoomStatus

type RoomNumber = Int

final case class Room(
    roomType: Int,
    status: RoomStatus)

final case class Hotel(
    name: String,
    address: Address,
    rooms: HashMap[RoomNumber, Room])

final case class HotelData(name: String, address: Address)
final case class BookedRoom(
    roomNumber: RoomNumber,
    dateTime: LocalDate,
    customer: CustomerDataResponse)

enum HotelOpsError:
  case HotelNotFound, RoomNotFound, RoomIsNotAvailable
end HotelOpsError

trait HotelOps:
  def getHotels(): List[HotelData]

  def getHotelAvailableRooms(hotelName: String, roomType: Option[RoomType])
      : Either[HotelOpsError, List[RoomNumber]]

  def bookRoom(
      hotelName: String,
      roomNumber: RoomNumber,
      customerId: String,
    ): Either[HotelOpsError | CustomerOpsError, BookedRoom]

end HotelOps
