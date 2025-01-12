package com.renghen.session

import java.util.UUID

import com.renghen.customer.CustomerDataResponse
import java.time.LocalDateTime

final case class Session(
    id: UUID,
    customer: CustomerDataResponse,
    lastAccess: LocalDateTime)

enum SessionOpErrors:
  case SessionNotFound, SessionExpired, SessionAlreadyExist
end SessionOpErrors

trait SessionOps:
  def create(customer: CustomerDataResponse): Either[SessionOpErrors, Session]
  def remove(id: UUID): Either[SessionOpErrors, Session]
end SessionOps
