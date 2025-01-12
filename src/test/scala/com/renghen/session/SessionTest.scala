package com.renghen.session

import zio.*
import zio.test.*

import com.renghen.customer.CustomerMemoryLive

object SessionTest extends ZIOSpecDefault:
  override def spec = suite("SessionSpec")(
    test("create Session") {
      val sessionRepo   = SessionMemoryLive()
      val customerRepo  = CustomerMemoryLive()
      val id            = "1"
      val customerFound = ZIO.fromEither(customerRepo.findCustomerById(id))

      for
        customer <- customerFound
        session  <- ZIO.fromEither(sessionRepo.create(customer))
        error    <- ZIO.fromEither(sessionRepo.create(customer)).flip
      yield assertTrue(
        session.customer.title == s"title_$id" && session.customer.firstName == s"firstName_$id" &&
        session.customer.lastName == s"lastName_$id" &&
        session.customer.username == s"username_$id" &&
        session.customer.mobile == id && session.customer.email == Some(s"email_$id@test.com") &&
        session.customer.address.street == s"Street_$id" &&
        session.customer.address.city == s"City_$id" &&
        session.customer.address.country == "Mauritius",
        error == SessionOpErrors.SessionAlreadyExist,
      )
      end for
    },
    test("remove Session") {
      val sessionRepo   = SessionMemoryLive()
      val customerRepo  = CustomerMemoryLive()
      val id            = "1"
      val customerFound = ZIO.fromEither(customerRepo.findCustomerById(id))

      for
        customer       <- customerFound
        session        <- ZIO.fromEither(sessionRepo.create(customer))
        removedSession <- ZIO.fromEither(sessionRepo.remove(session.id))
        error          <- ZIO.fromEither(sessionRepo.remove(session.id)).flip
      yield assertTrue(
        removedSession.customer.title == s"title_$id" &&
        removedSession.customer.firstName == s"firstName_$id" &&
        removedSession.customer.lastName == s"lastName_$id" &&
        removedSession.customer.username == s"username_$id" &&
        removedSession.customer.mobile == id &&
        removedSession.customer.email == Some(s"email_$id@test.com") &&
        removedSession.customer.address.street == s"Street_$id" &&
        removedSession.customer.address.city == s"City_$id" &&
        removedSession.customer.address.country == "Mauritius",
        error == SessionOpErrors.SessionNotFound,
      )
      end for
    },
  )
end SessionTest
