package com.renghen.hotel

import com.renghen.Endpoints.Base

import sttp.tapir.ztapir.*
import sttp.tapir.json.jsoniter.*
import sttp.tapir.Schema

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import zio.ZIO

object JsonGiven:
  import com.renghen.common.Address.given
  given JsonValueCodec[HotelData]       = JsonCodecMaker.make
  given JsonValueCodec[List[HotelData]] = JsonCodecMaker.make
  given JsonValueCodec[HotelOpsError]   = JsonCodecMaker.make

  given Schema[HotelData]     = Schema.derived
  given Schema[HotelOpsError] = Schema.derived
end JsonGiven

object Endpoints:
  import JsonGiven.given
  given JsonValueCodec[List[String]] = JsonCodecMaker.make
  private val hotelBase              = Base.in("hotel")
  private val getAllHotels           = hotelBase.get.out(jsonBody[List[HotelData]])
  private val getAllRoomByHotel      = hotelBase
    .get
    .in(path[String])
    .out(jsonBody[List[String]])
    .errorOut(jsonBody[HotelOpsError])

  def getEndpoints(hotelOps: HotelOps) =
    val allHotels = getAllHotels.zServerLogic(_ => ZIO.succeed(hotelOps.getHotels()))

    val allRoomByHotel = getAllRoomByHotel.zServerLogic{ hotelName =>
      ZIO.fromEither(hotelOps.getHotelAvailableRooms(hotelName, None).map(_.map(_.toString())))
    }

    List(allHotels, allRoomByHotel)
  end getEndpoints

end Endpoints
