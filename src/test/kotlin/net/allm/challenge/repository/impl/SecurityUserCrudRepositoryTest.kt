package net.allm.challenge.repository.impl

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@DataJpaTest
internal class SecurityUserCrudRepositoryTest @Autowired constructor(
        private val repository: SecurityUserCrudRepository
) {

    @Test
    fun `user by username should be successfully fetched`() {
        val username = "leif"
        val entity = repository.findById(username)

        entity.get().username shouldBe username
        BCryptPasswordEncoder().matches("password", entity.get().password) shouldBe true
        entity.get().enabled shouldBe true
    }

}