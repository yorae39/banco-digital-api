package com.example.bancodigital.service

import com.example.bancodigital.model.Role
import com.example.bancodigital.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService { // Import do org.springframework.security.core.userdetails.UserDetailsService

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            buildSimpleGrantAuthorities(user.roles)
        )
    }

    private fun buildSimpleGrantAuthorities(roles: Set<Role>): List<SimpleGrantedAuthority> {
        return roles.map { role ->
            val name = role.roleType.name
            // Se já começar com "ROLE_", usa como está. Se não, adiciona "ROLE_".
            val formattedRole = if (name.startsWith("ROLE_")) name else "ROLE_$name"

            SimpleGrantedAuthority(formattedRole)
        }
    }
}
