package com.kotlinspringvue.backend.jwt

import com.kotlinspringvue.backend.jpa.User
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtProvider {

    private val logger: Logger = LoggerFactory.getLogger(JwtProvider::class.java)

    @Value("\${prototype.app.jwtSecret}")
    lateinit var jwtSecret: String

    @Value("\${prototype.app.jwtExpiration}")
    var jwtExpiration: Int? = 0

    fun generateJwtToken(user: User): String {
        return Jwts.builder()
                .setSubject(user.username)
                .setIssuedAt(Date())
                .setExpiration(Date((Date()).time + jwtExpiration!! * 1000))
                .claim("roles", user.roles?.map { it.name })
                .claim("enabled", user.enabled)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature -> Message: {} ", e)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token -> Message: {}", e)
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token -> Message: {}", e)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token -> Message: {}", e)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty -> Message: {}", e)
        }

        return false
    }

    fun getUserNameFromJwtToken(token: String): String {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body.subject
    }
}