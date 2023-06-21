package net.allm.challenge.service.impl

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.allm.challenge.repository.api.SecurityUserRepository
import org.junit.jupiter.api.Test
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException

class SecurityUserDetailsServiceTest {

    private val repository = mockk<SecurityUserRepository>()
    private val tested = SecurityUserDetailsService(repository)

    @Test
    fun `when repository returns user then this user should be passed to the result`() {
        val username = "username"
        val fetchedUser = User(username, "", listOf())
        every {
            repository.getUserByUsername(username)
        } answers {
            fetchedUser
        }

        tested.loadUserByUsername(username) shouldBe fetchedUser
        verify { repository.getUserByUsername(username) }
        confirmVerified(repository)
    }

    @Test
    fun `when repository returns null then UsernameNotFoundException should be thrown`() {
        val username = "username"
        every {
            repository.getUserByUsername(username)
        } answers {
            null
        }

        shouldThrowExactly<UsernameNotFoundException> { tested.loadUserByUsername(username) }
        verify { repository.getUserByUsername(username) }
        confirmVerified(repository)
    }

    @Test
    fun `when requested with null username then UsernameNotFoundException should be thrown`() {
        val username = null
        shouldThrowExactly<UsernameNotFoundException> { tested.loadUserByUsername(username) }
        confirmVerified(repository)
    }

}