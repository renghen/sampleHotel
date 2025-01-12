package com.renghen.session

import collection.mutable.HashMap
import java.util.UUID
import java.time.LocalDateTime

import com.renghen.customer.CustomerDataResponse

class SessionMemoryLive extends SessionOps:

  override def create(customer: CustomerDataResponse): Either[SessionOpErrors, Session] =
    registry.values.find(sess => sess.customer.username == customer.username) match
      case None    =>
        val uuid    = UUID.randomUUID()
        val session = Session(uuid, customer, LocalDateTime.now())
        registry.addOne(uuid, session)
        Right(session)
      case Some(v) =>
        Left(SessionOpErrors.SessionAlreadyExist)

  override def remove(id: UUID): Either[SessionOpErrors, Session] =
    registry.find((key, value) => key == id) match
      case None             => Left(SessionOpErrors.SessionNotFound)
      case Some(_, session) =>
        registry.remove(id)
        Right(session)

  private val registry = HashMap.empty[UUID, Session]
end SessionMemoryLive
