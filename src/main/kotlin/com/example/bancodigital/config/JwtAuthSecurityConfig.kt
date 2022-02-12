package com.example.bancodigital.config

import com.example.bancodigital.model.RoleType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class JwtAuthSecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtRequestFilter: JwtRequestFilter
): WebSecurityConfigurerAdapter() {


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        //DESABILITAR CSRF
        httpSecurity.csrf().disable() // Somente o administrador pode executar a operação de exclusão HTTP
            .authorizeRequests().antMatchers(HttpMethod.DELETE)
            .hasRole(RoleType.ADMIN.name) // qualquer usuário autenticado pode realizar todas as outras operações
            .antMatchers("/holders/**", "/accounts/**", "/address/**", "/transactions/**")
            .hasAnyRole(RoleType.ADMIN.name, RoleType.USER.name) //Permite todas as outras requisições sem autenticação
            .and().authorizeRequests().anyRequest()
            .permitAll() // Rejeita todas as solicitações não autenticadas e envia o código de erro 401.
            .and().exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Não precisamos criar sessões.
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Adiciona um filtro para validar os tokens a cada solicitação
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

}