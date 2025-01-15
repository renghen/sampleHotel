package com.renghen.auth

import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.{JWT, JWTVerifier}

import zio.ZIO.ifZIO
import zio.{IO, Task, ZIO, ZLayer}

import java.time.{Duration, Instant}
import java.util.UUID
import scala.util.{Success, Try}
import com.renghen.customer.CustomerOpsError

class JwtService:
  final private val Issuer    = "renghen"
  final private val ClaimName = "userId"
  final private val JWTSecret = "somesecret"

  final private val algorithm: Algorithm  = Algorithm.HMAC256(JWTSecret)
  final private val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(Issuer).build()

  def generate(userId: String): Either[CustomerOpsError, String] =
    val now: Instant = Instant.now()
    Try(
      JWT
        .create()
        .withIssuer(Issuer)
        .withClaim(ClaimName, userId)
        .withIssuedAt(now)
        .withExpiresAt(now.plus(Duration.ofHours(1)))
        .withJWTId(UUID.randomUUID().toString)
        .sign(algorithm),
    ) match
      case Success(createdJwt) => Right(createdJwt)
      case _                   => Left(CustomerOpsError.JWTerror("Problem with JWT generation!"))
    end match
  end generate

  def verify(jwtToken: String): Either[CustomerOpsError, String] =
    Try(verifier.verify(jwtToken)) match
      case Success(decodedJwt) => Right(decodedJwt.getClaim(ClaimName).asString())
      case _                   =>
        // can add logic to also remove session or black list a session
        Left(CustomerOpsError.Unauthorized("Invalid token!"))
end JwtService

object JwtService:
  val live = ZLayer(ZIO.succeed(new JwtService))
end JwtService
