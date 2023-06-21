package net.allm.challenge.repository.impl

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.allm.challenge.repository.impl.entity.SecurityUserEntity
import org.junit.jupiter.api.Test
import java.util.*

class SecurityUserRepositoryImplTest {

    private val crud = mockk<SecurityUserCrudRepository>()

    private val tested = SecurityUserRepositoryImpl(crud)

    @Test
    fun `when user is found then according User object should be returned`() {

        val username = "leif"
        val passwordHash = "pass"
        val enabled = true
        every {
            crud.findById(username)
        } answers {
            Optional.of(SecurityUserEntity().apply {
                this.username = username
                this.password = passwordHash
                this.enabled = enabled
            })
        }

        val found = tested.getUserByUsername(username)

        found shouldNotBe null
        found!!.username shouldBe username
        found.password shouldBe passwordHash
        verify { crud.findById(username) }
        confirmVerified(crud)
    }

    @Test
    fun `when entity not found then null should be returned`() {
        val username = "leif"
        every {
            crud.findById(username)
        } answers {
            Optional.empty()
        }
        tested.getUserByUsername(username) shouldBe null
        verify { crud.findById(username) }
        confirmVerified(crud)
    }

    @Test
    fun `when entity is found but not available then null should be returned`() {
        val username = "leif"
        val passwordHash = "pass"
        every {
            crud.findById(username)
        } answers {
            Optional.of(SecurityUserEntity().apply {
                this.username = username
                this.password = passwordHash
                this.enabled = false
            })
        }
        tested.getUserByUsername(username) shouldBe null
        verify { crud.findById(username) }
        confirmVerified(crud)
    }

}
