package net.allm.challenge.repository.impl

import io.kotest.matchers.shouldBe
import net.allm.challenge.repository.impl.entity.HospitalEntity
import net.allm.challenge.repository.impl.entity.PatientEntity
import net.allm.challenge.repository.impl.entity.VisitEntity
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class EntityMappersKtTest {

    @Test
    fun `hospital entity should be correctly mapped to domain level object`() {
        val original = HospitalEntity()
                .apply {
                    id = 1
                    name = "some name"
                }

        val mapped = original.toDomain()

        mapped.id shouldBe original.id
        mapped.name shouldBe original.name
    }

    @Test
    fun `patient entity should be correctly mapped to domain`() {
        val original = PatientEntity().apply {
            id = 1
            name = "some name"
            username = "username"
        }
        val mapped = original.toDomain()

        mapped.id shouldBe original.id
        mapped.name shouldBe original.name
        mapped.username shouldBe original.username
    }

    @Test
    fun `visit entity should be translated to domain correctly`() {
        val dateTime = LocalDateTime.now()
        val original = VisitEntity()
                .also {
                    it.id = 1L
                    it.hospital = HospitalEntity()
                            .also {
                                it.id = 2L
                                it.name = "hospital name"
                            }
                    it.patient = PatientEntity()
                            .also {
                                it.id = 3L
                                it.name = "patientName"
                                it.username = "user name"
                            }
                    it.dateTime = dateTime
                }
        val mapped = original.toDomain()
        mapped.id shouldBe original.id
        mapped.dateTime shouldBe dateTime
        mapped.hospital shouldBe original.hospital.toDomain()
        mapped.patient shouldBe original.patient.toDomain()
    }

}