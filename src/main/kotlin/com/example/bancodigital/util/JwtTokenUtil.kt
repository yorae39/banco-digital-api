package com.example.bancodigital.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Component
class JwtTokenUtil {

    // 5 horas em milissegundos (5 * 60 * 60 * 1000)
    val JWT_TOKEN_VALIDITY: Long = 5 * 60 * 60 * 1000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    // Helper privado para obter a SecretKey formatada no padrão 0.12.x
    private fun getSigningKey(): SecretKey {
        return Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    }

    fun getUsernameFromToken(token: String?): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    fun getExpirationDateFromToken(token: String?): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.expiration }
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        val now = System.currentTimeMillis()

        return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(Date(now))
            .expiration(Date(now + JWT_TOKEN_VALIDITY))
            .signWith(getSigningKey()) // Usa a nova assinatura com a SecretKey segura
            .compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }
}