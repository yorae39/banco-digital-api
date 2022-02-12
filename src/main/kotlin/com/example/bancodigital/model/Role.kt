package com.example.bancodigital.model

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToMany(mappedBy = "roles")
    val users: Set<User>,
    @Enumerated(EnumType.STRING)
    val roleType: RoleType
) {
    override fun equals(other: Any?): Boolean {
        val otherObject = other as? Role ?: return false
        return otherObject.id == id && otherObject.roleType == roleType
    }

    override fun hashCode(): Int = id.hashCode() * 31 + roleType.hashCode()
}