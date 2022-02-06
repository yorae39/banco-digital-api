package com.example.bancodigital.repository

import com.example.bancodigital.model.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long> {
}