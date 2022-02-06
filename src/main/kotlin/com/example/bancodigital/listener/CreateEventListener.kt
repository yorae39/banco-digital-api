package com.example.bancodigital.listener

import com.example.bancodigital.event.CreateEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.servlet.http.HttpServletResponse

@Component
class CreateEventListener : ApplicationListener<CreateEvent> {
    override fun onApplicationEvent(event: CreateEvent) {
        val response = event.httpServletResponse
        val id = event.id
        addLocalHeader(response, id)
    }

    private fun addLocalHeader(response: HttpServletResponse, id: Long) {
        val uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/id").buildAndExpand(id).toUri()
        response.setHeader("Location", uri.toASCIIString())
    }

}