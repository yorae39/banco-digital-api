package com.example.bancodigital.model

import javax.persistence.Entity
import javax.persistence.FetchType
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = [JoinColumn(name = "userId", referencedColumnName = "id",
            nullable = false, updatable = false)],
        inverseJoinColumns = [JoinColumn(name = "roleId", referencedColumnName = "id",
            nullable = false, updatable = false)])
    val roles: Set<Role>
) {
    override fun equals(other: Any?): Boolean {
        val otherObject = other as? User ?: return false
        return otherObject.id == id && otherObject.username == username && otherObject.password == password
    }

    override fun hashCode(): Int = id.hashCode() * 31 + username.hashCode() + password.hashCode()
}