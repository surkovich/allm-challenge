package net.allm.challenge.service.impl

import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.allm.challenge.repository.api.HospitalRepository
import net.allm.challenge.service.api.Hospital
import org.junit.jupiter.api.Test

class HospitalServiceImplTest {

    private val repository = mockk<HospitalRepository>()

    private val tested = HospitalServiceImpl(repository)

    @Test
    fun `service find request should be plainly passed to repository layer`() {
        val namePart = "some name part"
        val page = 3
        val size = 15
        val listToBeReturned = listOf(Hospital(id = 1, name = namePart))
        every {
            repository.find(namePart = namePart,
                    page = page,
                    size = size)
        } answers {
            listToBeReturned
        }

        val resultRetrieved = tested.find(namePart, page, size)

        resultRetrieved shouldBe listToBeReturned

        verify { repository.find(namePart = namePart, page = page, size = size) }
        confirmVerified(repository)
    }

}