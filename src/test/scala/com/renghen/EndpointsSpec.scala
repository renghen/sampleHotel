package com.renghen

import com.renghen.Endpoints.{*, given}
import sttp.client3.testing.SttpBackendStub
import sttp.client3.{basicRequest, UriContext}
import sttp.tapir.server.stub.TapirStubInterpreter
import zio.test.Assertion.*
import zio.test.{assertZIO, ZIOSpecDefault}

import Library.*
import sttp.client3.jsoniter.*
import sttp.tapir.ztapir.RIOMonadError

object EndpointsSpec extends ZIOSpecDefault:
  def spec = suite("Endpoints spec")(
    test("return hello message") {
      // given
      val backendStub = TapirStubInterpreter(SttpBackendStub(new RIOMonadError[Any]))
        .whenServerEndpointRunLogic(helloServerEndpoint)
        .backend()

      // when
      val response = basicRequest
        .get(uri"http://test.com/hello?name=adam")
        .send(backendStub)

      // then
      assertZIO(response.map(_.body))(isRight(equalTo("Hello adam")))
    },
    test("list available books") {
      // given
      val backendStub = TapirStubInterpreter(SttpBackendStub(new RIOMonadError[Any]))
        .whenServerEndpointRunLogic(booksListingServerEndpoint)
        .backend()

      // when
      val response = basicRequest
        .get(uri"http://test.com/books/list/all")
        .response(asJson[List[Book]])
        .send(backendStub)

      // then
      assertZIO(response.map(_.body))(isRight(equalTo(books)))
    },
  )

end EndpointsSpec
