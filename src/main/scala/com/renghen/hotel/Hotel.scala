package com.renghen.hotel

import com.renghen.common.Address
import com.renghen.customer.{Customer, CustomerDataResponse}
import com.renghen.customer.CustomerOpsError
import com.renghen.session.SessionOpErrors

import collection.mutable.HashMap
import java.time.LocalDate

enum RoomStatus:
  case Available
  case Booked(durationInDays: Int, since: LocalDate)
  case Occupied(durationInDays: Int, since: LocalDate)

end RoomStatus

type RoomNumber = Int

final case class Room(
    roomType: RoomType,
    status: RoomStatus)

final case class Hotel(
    name: String,
    address: Address,
    rooms: HashMap[RoomNumber, Room])

final case class HotelData(name: String, address: Address)
final case class BookedRoom(
    roomNumber: RoomNumber,
    roomType: RoomType,
    dateTime: LocalDate,
    durationDay: Int,
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
      durationInDays: Int,
      customerId: String,
    ): Either[HotelOpsError | SessionOpErrors, BookedRoom]

end HotelOps
