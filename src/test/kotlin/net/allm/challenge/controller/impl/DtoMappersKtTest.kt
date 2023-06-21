package net.allm.challenge.controller.impl

import io.kotest.matchers.shouldBe
import net.allm.challenge.controller.api.HospitalResponseDto
import net.allm.challenge.service.api.Hospital
import org.junit.jupiter.api.Test

internal class DtoMappersKtTest {

    @Test
    fun `hospital domain object should be correctly parsed to DTO response`() {
        val hospital = Hospital(
                id = 1,
                name = "some name"
        )

        val parsed = hospital.toDto()
        parsed shouldBe HospitalResponseDto(
                id = hospital.id,
                name = hospital.name
        )
    }
}