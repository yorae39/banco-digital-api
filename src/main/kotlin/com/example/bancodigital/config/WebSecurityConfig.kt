package com.example.bancodigital.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    /*
        CÓDIGO INICIAL PARA TER ACESSO DE TESTE VIA POSTMAN!
        ESSA LÓGICA SERÁ MELHORADA...
    */
    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable().authorizeRequests()
            .antMatchers("/holders/**").permitAll()
            .and().exceptionHandling().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}