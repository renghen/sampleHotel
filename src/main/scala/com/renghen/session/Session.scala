package com.renghen.session

import java.util.UUID

import com.renghen.customer.CustomerDataResponse
import java.time.LocalDateTime

final case class Session(
    id: UUID,
    valid: Boolean,
    customer: CustomerDataResponse,
    lastAccess: LocalDateTime)

enum SessionOpErrors:
  case SessionNotFound, SessionExpired
end SessionOpErrors

trait SessionOps:
  def create(customer: CustomerDataResponse): Session
  def disable(id: UUID): Either[SessionOpErrors, Session]
  def isvalid(id: UUID): Either[SessionOpErrors, Session]
end SessionOps
