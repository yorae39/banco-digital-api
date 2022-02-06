package com.example.bancodigital.repository

import com.example.bancodigital.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
     fun findByRoleType(roleType: String)
}