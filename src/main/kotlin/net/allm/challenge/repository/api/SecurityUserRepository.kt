package net.allm.challenge.repository.api

import org.springframework.security.core.userdetails.User

interface SecurityUserRepository {
    fun getUserByUsername(username: String): User?
}