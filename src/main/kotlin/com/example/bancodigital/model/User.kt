package com.example.bancodigital.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.FetchType
import jakarta.persistence.JoinTable
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val username: String,
    val password: String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "userId", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "roleId", referencedColumnName = "id")]
    )
    val roles: Set<Role>
) {
    override fun equals(other: Any?): Boolean {
        val otherObject = other as? User ?: return false
        return otherObject.id == id && otherObject.username == username && otherObject.password == password
    }

    override fun hashCode(): Int = id.hashCode() * 31 + username.hashCode() + password.hashCode()
}