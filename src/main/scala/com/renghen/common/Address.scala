package com.renghen.common

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import sttp.tapir.Schema

final case class Address(
    street: String,
    city: String,
    country: String)

object Address:
  given JsonValueCodec[Address]       = JsonCodecMaker.make
  given JsonValueCodec[List[Address]] = JsonCodecMaker.make
  given Schema[Address]               = Schema.derived
end Address
