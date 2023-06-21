package net.allm.challenge.controller

import io.kotest.matchers.shouldBe
import io.mockk.*
import net.allm.challenge.controller.impl.*
import net.allm.challenge.service.api.HospitalService
import org.junit.jupiter.api.Test

class HospitalControllerTest {

    private val service = mockk<HospitalService>()
    private val controller = HospitalController(service)

    @Test
    fun `searching for hospitals with all parameters set should pass to service layer`() {
        val namePart = "some name"
        val page = 0
        val pageSize = 20
        every {
            service.find(namePart = namePart, page = page, size = pageSize)
        } answers {
            emptyList()
        }

        controller.getAllHospitals(
                namePart = namePart, page = page, size = pageSize
        )

        verify { service.find(namePart = namePart, page = page, size = pageSize) }
        confirmVerified(service)
    }

    @Test
    fun `searching for hospitals with null parameters should invoke search with default parameters values`() {
        every {
            service.find(namePart = EMPTY_NAME_PART, page = DEFAULT_PAGE, size = DEFAULT_PAGE_SIZE)
        } answers {
            emptyList()
        }

        controller.getAllHospitals(null, null, null)
        verify { service.find(namePart = EMPTY_NAME_PART, page = DEFAULT_PAGE, size = DEFAULT_PAGE_SIZE) }
        confirmVerified(service)
    }

    @Test
    fun `search for hospitals with a page size over the limit should invoke search with default limit`() {
        val namePart = "some name"
        val page = 0
        val pageSize = slot<Int>()
        every {
            service.find(namePart = namePart, page = page, size = capture(pageSize))
        } answers {
            emptyList()
        }

        controller.getAllHospitals(
                namePart = namePart, page = page, size = 1000
        )

        pageSize.captured shouldBe DEFAULT_PAGE_SIZE_LIMIT

        verify { service.find(namePart = namePart, page = page, size = DEFAULT_PAGE_SIZE_LIMIT) }
        confirmVerified(service)
    }

}