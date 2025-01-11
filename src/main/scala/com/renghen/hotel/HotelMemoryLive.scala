package com.renghen.hotel

import com.renghen.common.Address
import scala.collection.mutable.HashMap

import java.util.Random
import java.time.LocalDateTime

object HotelMemoryLive extends HotelOperations:

  override def getHotels(): List[HotelData] = hotels.map(h => HotelData(h.name, h.address)).toList

  override def getHotelAvailableRooms(hotelName: String, roomType: Option[RoomType])
      : Either[HotelOperationError, List[RoomNumber]] =
    val hotel = hotels.find(h => h.name == hotelName)

    hotel match
      case None        => Left(HotelOperationError.HotelNotFound)
      case Some(value) =>
        val roomsAvailable = roomType match
          case None => value.rooms.filter((roomNumber, room) => room.status == RoomStatus.Available)
          case Some(typeOfRoom) =>
            value
              .rooms
              .filter((roomNumber, room) =>
                room.status == RoomStatus.Available && room.roomType == typeOfRoom,
              )

        Right(roomsAvailable.keys.toList)
    end match

  end getHotelAvailableRooms

  override def bookRoom(
      hotelName: String,
      roomNumber: RoomNumber,
      customerId: String,
    ): Either[HotelOperationError, BookedRoom] =
    val hotelFound = hotels.find(h => h.name == hotelName)
    hotelFound match
      case None        => Left(HotelOperationError.HotelNotFound)
      case Some(hotel) =>
        hotel.rooms.get(roomNumber) match
          case None       => Left(HotelOperationError.RoomNotFound)
          case Some(room) =>
            room.status match
              case RoomStatus.Available =>
                val bookedRoom = BookedRoom(roomNumber, LocalDateTime.now(), customerId)
                Right(bookedRoom)
              case RoomStatus.Booked    => Left(HotelOperationError.RoomIsNotAvailable)
              case RoomStatus.Occupied  => Left(HotelOperationError.RoomIsNotAvailable)
    end match
  end bookRoom

  val rand        = Random()
  val randomRange = RoomTypes.types.length

  private def generateRooms(n: Int): HashMap[Int, Room] =
    val roomsBuilder = HashMap.newBuilder[Int, Room]
    for i <- 0 until n do
      roomsBuilder.addOne(i, Room(rand.nextInt(randomRange), RoomStatus.Available))
    end for

    roomsBuilder.result()
  end generateRooms

  private val hotels = (for i <- 0 until 10
  yield
    val address = Address(s"Street_$i", s"City_$i", "Mauritius")
    Hotel(s"Hotel_$i", address, generateRooms(10))
  ).toArray

end HotelMemoryLive
