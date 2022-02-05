package com.example.bancodigital.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val username: String,
    val password: String,
    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = [JoinColumn(name = "userId", referencedColumnName = "id",
            nullable = false, updatable = false)],
        inverseJoinColumns = [JoinColumn(name = "roleId", referencedColumnName = "id",
            nullable = false, updatable = false)])
    val roles: Set<Role>
)