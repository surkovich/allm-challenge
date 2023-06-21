package net.allm.challenge.repository.impl

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainIgnoringCase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable


@DataJpaTest
internal class HospitalJpaRepositoryTest @Autowired constructor(
        val repository: HospitalJpaRepository
) {

    @Test
    fun `Pageable search should not fail`() {
        repository.findByNameIsContainingIgnoreCase("", Pageable.unpaged())
    }

    @Test
    fun `search should return entity by exact name`() {

        val nameToSearch = "Flowerhill General Hospital"
        val found = repository.findByNameIsContainingIgnoreCase(nameToSearch, Pageable.unpaged())

        found.size shouldBe 1
        found[0].name shouldBe nameToSearch
    }

    @Test
    fun `search by name part should return according name`() {
        val namePattern = "LOWeRhILL"
        repository.findByNameIsContainingIgnoreCase(namePattern, Pageable.unpaged())[0]
                .name shouldContainIgnoringCase namePattern
    }



}