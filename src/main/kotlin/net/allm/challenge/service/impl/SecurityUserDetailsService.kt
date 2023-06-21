package net.allm.challenge.service.impl

import net.allm.challenge.repository.api.SecurityUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SecurityUserDetailsService @Autowired constructor(
        private val repository: SecurityUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        if (username == null)
            throw UsernameNotFoundException("Anonymous access prohibited")

        return repository.getUserByUsername(username) ?: throw UsernameNotFoundException("Username not found or inactive")
    }
}