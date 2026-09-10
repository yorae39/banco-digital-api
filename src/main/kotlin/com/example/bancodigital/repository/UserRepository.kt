package com.example.bancodigital.repository

import com.example.bancodigital.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    fun findByUsername(username: String): User?
}
/*
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(userName: String): User?
}*/
