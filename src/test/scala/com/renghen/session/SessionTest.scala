package com.renghen.session

import zio.*
import zio.test.*

import com.renghen.customer.CustomerMemoryLive
import java.util.UUID

object SessionTest extends ZIOSpecDefault:
  override def spec = suite("SessionSpec")(
    test("create Session") {
      val sessionRepo   = SessionMemory()
      val customerRepo  = CustomerMemoryLive()
      val id            = "1"
      val customerFound = ZIO.fromEither(customerRepo.findCustomerById(id))

      for
        customer    <- customerFound
        session     <- ZIO.fromEither(sessionRepo.create(customer))
        errorCreate <- ZIO.fromEither(sessionRepo.create(customer)).flip
        sessionGet  <- ZIO.fromEither(sessionRepo.get(session.id))
        errorGet    <- ZIO.fromEither(sessionRepo.get(UUID.randomUUID())).flip
      yield assertTrue(
        session.customer.title == s"title_$id" && session.customer.firstName == s"firstName_$id" &&
        session.customer.lastName == s"lastName_$id" &&
        session.customer.username == s"username_$id" &&
        session.customer.mobile == id && session.customer.email == Some(s"email_$id@test.com") &&
        session.customer.address.street == s"Street_$id" &&
        session.customer.address.city == s"City_$id" &&
        session.customer.address.country == "Mauritius",
        errorCreate == SessionOpErrors.SessionAlreadyExist,
        sessionGet.customer == session.customer,
        errorGet == SessionOpErrors.SessionNotFound,
      )
      end for
    },
    test("remove Session") {
      val sessionRepo   = SessionMemory()
      val customerRepo  = CustomerMemoryLive()
      val id            = "1"
      val customerFound = ZIO.fromEither(customerRepo.findCustomerById(id))

      for
        customer       <- customerFound
        session        <- ZIO.fromEither(sessionRepo.create(customer))
        removedSession <- ZIO.fromEither(sessionRepo.remove(session.id))
        errorRemove    <- ZIO.fromEither(sessionRepo.remove(session.id)).flip
        errorGet       <- ZIO.fromEither(sessionRepo.get(session.id)).flip
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
        errorRemove == SessionOpErrors.SessionNotFound,
        errorGet == SessionOpErrors.SessionNotFound,
      )
      end for
    },
  )
end SessionTest
