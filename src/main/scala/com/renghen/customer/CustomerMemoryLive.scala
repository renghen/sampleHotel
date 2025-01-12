package com.renghen.customer

import com.renghen.common.Address

import java.time.LocalDateTime
import io.scalaland.chimney.dsl._

class CustomerMemoryLive extends CustomerOps{  
  override def findCustomerById(customerId: String): Either[CustomerOpsError, CustomerDataResponse] = {
    customers.find(c => c.id == customerId) match
      case None => Left(CustomerOpsError.CustomerNotFound)
      case Some(cus) => 
        val cdr = cus.transformInto[CustomerDataResponse]
        Right(cdr)
  }

  private val customers = (for i <- 0 until 10
  yield
    val address = Address(s"Street_$i", s"City_$i", "Mauritius")
    Customer(i.toString(),s"username_$i",s"password_$i",s"title_$i",
    s"firstName_$i",
    s"lastName_$i",
    address,
    Some(s"email_$i@test.com"),
    i.toString(),
    LocalDateTime.now(),
    LocalDateTime.now())    
  ).toArray
}
