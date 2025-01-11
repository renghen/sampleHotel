package com.renghen.customer

import com.renghen.common.Address

// final case class CustomerData(
//     firstName: String,
//     lastName: String,
//     address: Address)

final case class Customer(
    id: String,
    username: String,
    password: String,
    firstName: String,
    lastName: String,
    address: Address,
    email: Option[String],
    mobile: String)
