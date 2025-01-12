package com.renghen.customer

import com.renghen.common.Address
import java.time.LocalDateTime

final case class Customer(
    id: String,
    username: String,
    password: String,
    title: String,
    firstName: String,
    lastName: String,
    address: Address,
    email: Option[String],
    mobile: String,
    dateCreated: LocalDateTime,
    dateModify: LocalDateTime)

enum CustomerOpsError:
  case CustomerNotFound
end CustomerOpsError

final case class CustomerDataOps(
    title: Option[String],
    password: Option[String],
    lastName: Option[String],
    address: Option[Address],
    email: Option[String],
    mobile: Option[String])

final case class CustomerDataResponse(
    title: String,
    firstName: String,
    lastName: String,
    username: String,
    address: Address,
    email: Option[String],
    mobile: String,
    dateCreated: LocalDateTime,
    dateModify: LocalDateTime)

trait CustomerOps:
  def findCustomerById(customerId: String): Either[CustomerOpsError, CustomerDataResponse]
  def updateCustomer(customerId: String, data: CustomerDataOps)
      : Either[CustomerOpsError, CustomerDataResponse]
end CustomerOps
