package com.example.bancodigital.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    val roleType: RoleType,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Role) return false
        return id != 0L && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    // Sobrescreve o toString SEM incluir a lista de usuários
    override fun toString(): String {
        return "Role(id=$id, roleType=$roleType)"
    }
}