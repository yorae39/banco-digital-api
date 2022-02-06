package com.example.bancodigital.repository

import com.example.bancodigital.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(userName: String)
}