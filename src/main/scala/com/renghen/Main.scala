package com.renghen

import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import zio.{Console, LogLevel, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.http.{Response, Routes, Server}
import zio.logging.LogFormat
import zio.logging.backend.SLF4J

object Main extends ZIOAppDefault:
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    SLF4J.slf4j(LogFormat.default)

  override def run: ZIO[ZIOAppArgs & Scope, Any, Any] =
    val serverOptions: ZioHttpServerOptions[Any] =
      ZioHttpServerOptions
        .customiseInterceptors
        .metricsInterceptor(Endpoints.prometheusMetrics.metricsInterceptor())
        .options

    val app: Routes[Any, Response] = ZioHttpInterpreter(serverOptions).toHttp(Endpoints.all)

    val port = sys.env.get("HTTP_PORT").flatMap(_.toIntOption).getOrElse(8080)

    (for
      actualPort <- Server.install(app) // or .serve if you don't need the port and want to keep it running without manual readLine
      _ <- Console.printLine(
        s"Go to http://localhost:${actualPort}/docs to open SwaggerUI. Press ENTER key to exit.",
      )
      _ <- Console.readLine
    yield ())
      .provide(
        ZLayer.succeed(Server.Config.default.port(port)),
        Server.live,
      )
      .exitCode

  end run

end Main
