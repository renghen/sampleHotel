package com.renghen

import sttp.tapir.*

import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker
import sttp.tapir.generic.auto.*
import sttp.tapir.json.jsoniter.*
import sttp.tapir.server.metrics.prometheus.PrometheusMetrics
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir.ZServerEndpoint
import zio.Task
import zio.ZIO

object Endpoints:

  val Base          = endpoint.in("api")
  val Authenticated = Base.securityIn(auth.bearer[String]())

  val publicEndpoints : List[ZServerEndpoint[Any, Any]]= List() //List(hotel.Endpoints.getEndpoints())

  val secureEndPoints = List()

  val apiEndpoints: List[ZServerEndpoint[Any, Any]] = publicEndpoints ++ secureEndPoints

  val docEndpoints: List[ZServerEndpoint[Any, Any]] = SwaggerInterpreter()
    .fromServerEndpoints[Task](apiEndpoints, "booking-hotel-service", "1.0.0")

  val prometheusMetrics: PrometheusMetrics[Task] = PrometheusMetrics.default[Task]()
  val metricsEndpoint: ZServerEndpoint[Any, Any] = prometheusMetrics.metricsEndpoint

  val all: List[ZServerEndpoint[Any, Any]] = apiEndpoints ++ docEndpoints ++ List(metricsEndpoint)

end Endpoints
