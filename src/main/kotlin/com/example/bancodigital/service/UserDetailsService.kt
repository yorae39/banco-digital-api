package com.example.bancodigital.service

import com.example.bancodigital.model.Role
import com.example.bancodigital.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    val userRepository: UserRepository
):UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
        return if (user != null) {
            User(
                user.username, user.password, buildSimpleGrantAuthorities(user.roles))
        } else {
            throw UsernameNotFoundException("User not found with username: $username")
        }
    }

    private fun buildSimpleGrantAuthorities(roles: Set<Role>): List<SimpleGrantedAuthority> {
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
        for (role in roles) {
            authorities.add(SimpleGrantedAuthority(role.roleType.name))
        }
        return authorities
    }

}