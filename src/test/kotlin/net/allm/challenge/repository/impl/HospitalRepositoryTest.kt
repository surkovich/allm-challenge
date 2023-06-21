package net.allm.challenge.repository.impl

import io.kotest.matchers.shouldBe
import io.mockk.*
import net.allm.challenge.repository.impl.entity.HospitalEntity
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

internal class HospitalRepositoryTest {

    private val jpaRepository = mockk<HospitalJpaRepository>()

    private val tested = HospitalRepositoryImpl(jpaRepository)

    @Test
    fun `searching by name part should transfer this name part to jpaRepository and result should match`() {
        val namePart = "some name part"

        val returnedEntities = listOf(
            HospitalEntity().apply { id = 1; name = namePart }
        )

        every {
            jpaRepository.findByNameIsContainingIgnoreCase(
                    namePart = "some name part",
                    pageable = Pageable.unpaged()
            )
        } answers  {
            returnedEntities
        }

        val found = tested.find(namePart, 1, 1)

        found shouldBe returnedEntities.toDomains()

        verify { jpaRepository.findByNameIsContainingIgnoreCase(
                namePart, Pageable.unpaged()
        ) }

        confirmVerified(jpaRepository)
    }

    @Test
    fun `searching with offset should call jpa with according pageable`() {
        val namePart = "some name part"

        val returnedEntities = listOf(
                HospitalEntity().apply { id = 1; name = namePart }
        )

        val page = 3
        val size = 10
        val expectedPageable = PageRequest.of(page, size)

        val capturedPageable = slot<Pageable>()

        every {
            jpaRepository.findByNameIsContainingIgnoreCase(
                    namePart = "some name part",
                    pageable = capture(capturedPageable)
            )
        } answers  {
            returnedEntities
        }

        val found = tested.find(namePart = namePart, page = page, size = size)

        found shouldBe returnedEntities.toDomains()

        capturedPageable.captured.isPaged shouldBe true
        capturedPageable.captured.pageNumber shouldBe page
        capturedPageable.captured.pageSize shouldBe size

        verify { jpaRepository.findByNameIsContainingIgnoreCase(
                namePart, expectedPageable) }

        confirmVerified(jpaRepository)

    }

}