package com.example.bancodigital.event

import org.springframework.context.ApplicationEvent
import jakarta.servlet.http.HttpServletResponse

class CreateEvent(source: Any?, val httpServletResponse: HttpServletResponse, val id: Long) :
    ApplicationEvent(source!!) {

    companion object {
        private const val serialVersionUID = 1L
    }

}