package com.example.bancodigital.config

import com.example.bancodigital.util.JwtTokenUtil
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    val jwtTokenUtil: JwtTokenUtil,
    val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
    ) {
        val requestTokenHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String? = null
        // O token JWT está no formato "Bearer token". Remova a palavra do portador e obtenha apenas o token
        // O token JWT está no formato "Bearer token". Remova a palavra do portador e obtenha apenas o token
        if (requestTokenHeader != null) {
            if (requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7)
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken)
                } catch (e: IllegalArgumentException) {
                    println("Unable to get JWT Token")
                } catch (e: ExpiredJwtException) {
                    println("JWT Token has expired")
                }
            } else {
                logger.warn("JWT Token does not begin with Bearer String")
            }
        }
        // Assim que obtivermos o token, validá-lo.
        // Assim que obtivermos o token, validá-lo.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
            // se o token for válido, configure o Spring Security para definir manualmente autenticação
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                val usernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                // Depois de definir a Autenticação no contexto, especificamos
                // que o usuário atual está autenticado. Então ele passa as
                // configurações de segurança do Spring com sucesso
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }

}