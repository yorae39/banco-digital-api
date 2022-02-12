package com.example.bancodigital.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Function

@Component
class JwtTokenUtil {
    val JWT_TOKEN_VALIDITY = (5 * 60 * 60).toLong()

    @Value("\${jwt.secret}")
    private val secret: String? = null

    // recupera o nome de usuário do token jwt
    fun getUsernameFromToken(token: String?): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    // recupera a data de expiração do token jwt
    fun getExpirationDateFromToken(token: String?): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.expiration }
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    // para recuperar qualquer informação do token, precisaremos da chave secreta
    fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    // verifica se o token expirou
    private fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    // gera token para usuário
    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    // Ao criar o token -
    // 1. Defina as declarações do token, como Emissor, Expiração, Assunto e o ID
    // 2. Assine o JWT usando o algoritmo HS512 e a chave secreta.
    // 3. De acordo com o JWS Compact
    // Serialização(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compactação do JWT para uma string segura para URL
    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JwtTokenUtil().JWT_TOKEN_VALIDITY + TimeUnit.MINUTES.toMillis(
                5)))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    //validar token
    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }
}