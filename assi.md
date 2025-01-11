you are tasked to create a "Booking Micro Service" that will be responsible for handling the 
customer journey of a hotel ecommerce platform with respect to accepting booking details from millions of customers.

You will create secure and tokenized REST endpoint that will carry out a given booking request atomically with the following operations:

1. accept booking details with number of occupants and dates of stay

2. accept customer information

3. accept hotel details

4. accept room details

 

When the above booking operations have completed,
either a successful or failure JSON message should be sent back as the payload.

In case of any errors during the operation, the client side should be aware of the message to be shown to the customer.
In addition, another JSON payload should be sent to a fictitious "Availability Micro Service" in order to update the quantity
 available for the given room_id at the hotel and whilst preserving FIFO ordering. A sample availability JSON payload is as follows:

 

In the above:

12345 is the hotel_id

6789 is the room_id

date signifies the booking date

100 signifies the availability on that date
```json
{
  "12345": {
    "6789": [
      {
        "01-09-2023": 100
      },

      {
        "02-09-2023": 99
      }
    ]
  }
}

```
 

Your Solution

- Your service should have all the necessary instructions for us to be able to run it.

- Ideally, you should consider Functional libraries, frameworks, and HTTP middlewares.

- It should expose REST endpoint tokenized via JWT tokens and with clear error handling.

- The project should have relevants tests that we should be able to run.

- You are free to make clear assumptions, mocks, and any further optimizations, as necessary.

 

Happy coding!