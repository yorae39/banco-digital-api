package com.example.bancodigital.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class JwtAuthSecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    @Lazy private val jwtRequestFilter: JwtRequestFilter
) {

    /*
     Para testar se o spring estava vendo esta classe
    init {
        println("🔥🔥🔥 JwtAuthSecurityConfig INSTANCIADA COM SUCESSO! 🔥🔥🔥")
    }*/

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    // O Spring Security gerencia o UserDetailsService automaticamente
    // através da AuthenticationConfiguration padrão do framework
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .cors { }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling { exception ->
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            .authorizeHttpRequests { auth ->
                // 1. Libera Swagger E a rota de Autenticação/Login
                auth.requestMatchers(
                    "/authenticate", // <-- ADICIONADO AQUI (Ajuste a URL do seu login se for diferente)
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()

                // 2. Operações de DELETE requerem ADMIN
                auth.requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                // 3. Rotas da API requerem ADMIN ou USER
                auth.requestMatchers(
                    "/holders/**",
                    "/accounts/**",
                    "/address/**",
                    "/transactions/**"
                ).hasAnyRole("ADMIN", "USER")

                // 4. Exige autenticação para qualquer outra rota não mapeada acima
                auth.anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtRequestFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}