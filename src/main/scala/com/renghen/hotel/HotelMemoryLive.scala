package com.renghen.hotel

import collection.mutable.HashMap

import java.util.Random
import java.time.LocalDate

import com.renghen.customer.CustomerOps
import com.renghen.common.Address
import com.renghen.customer.CustomerOpsError
import com.renghen.session.SessionOps
import java.util.UUID
import com.renghen.session.SessionOpErrors
final class HotelMemoryLive(session: SessionOps) extends HotelOps:
  override def getHotels(): List[HotelData] = hotels.map(h => HotelData(h.name, h.address)).toList

  override def getHotelAvailableRooms(hotelName: String, roomType: Option[RoomType])
      : Either[HotelOpsError, List[RoomNumber]] =
    val hotel = hotels.find(h => h.name == hotelName)

    hotel match
      case None        => Left(HotelOpsError.HotelNotFound)
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
      roomType: RoomType,
      durationInDays: Int,
      sessionId: String,
    ): Either[HotelOpsError | SessionOpErrors, BookedRoom] =
    val hotelFound = hotels.find(h => h.name == hotelName)
    hotelFound match
      case None        => Left(HotelOpsError.HotelNotFound)
      case Some(hotel) =>
        val roomsAvailable = hotel.rooms.filter((_,r) => r.roomType == roomType && r.status == RoomStatus.Available)
        if (roomsAvailable.size == 0) then
          Left(HotelOpsError.RoomIsNotAvailable)
        else            
          // TODO add logic for FIFO and room locking
          val roomNumber = ???
          session.get(UUID.fromString(sessionId)).map { sess =>
            BookedRoom(roomNumber, roomType,LocalDate.now(), durationInDays, sess.customer)
          }

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
