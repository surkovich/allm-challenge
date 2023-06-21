package net.allm.challenge.repository.impl

import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class PatientsCrudRepositoryTest @Autowired constructor(
        private val repository: PatientsCrudRepository
) {

    /**
     * Avoid testing the whole framework, just simple case to ensure setup is ok
     */
    @Test
    fun `find patient by username should work`() {
        val username = "leif"
        repository.findDistinctByUsername(username) shouldNotBe null
    }

}