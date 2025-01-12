package com.renghen.customer

import zio.*
import zio.test.*

object CustomerTest extends ZIOSpecDefault:
  val customerRepo  = CustomerMemoryLive()
  override def spec = suite("CustomerSpec")(
    test("customer found") {
      val id            = "1"
      val customerFound = ZIO.fromEither(customerRepo.findCustomerById(id))
      for customer <- customerFound
      yield assertTrue(
        customer.title == s"title_$id" && customer.firstName == s"firstName_$id" &&
        customer.lastName == s"lastName_$id" && customer.username == s"username_$id" &&
        customer.mobile == id && customer.email == Some(s"email_$id@test.com") &&
        customer.address.street == s"Street_$id" && customer.address.city == s"City_$id" &&
        customer.address.country == "Mauritius",
      )
    },
    test("customer NOT found") {
      val id               = "11"
      val customerNotFound = ZIO.fromEither(customerRepo.findCustomerById(id)).flip
      for error <- customerNotFound
      yield assertTrue(error == CustomerOpsError.CustomerNotFound)

    },
  )

end CustomerTest
