package com.renghen.hotel

import collection.mutable.Buffer

type RoomType = Int // TODO refactor to a newtype or Opaque

object RoomTypes:
  val types: Buffer[String] = Buffer(
    "Suite",
    "Connecting rooms",
    "Double room",
    "Presidential Suites",
    "Single room",
    "Deluxe room",
    "Triple room",
    "Studio",
    "Standard room",
    "Twin room",
    "Penthouse Suites",
    "Standard hotel rooms",
    "Studio hotel rooms",
    "Bridal suites",
    "Honeymoon suites",
    "Junior Suite",
    "Accessible room",
    "Executive suite",
    "Queen room",
    "Standard suite rooms",
    "Deluxe hotel rooms",
    "Hollywood Twin",
    "Junior Suites",
    "Quadruple room",
  )

end RoomTypes
