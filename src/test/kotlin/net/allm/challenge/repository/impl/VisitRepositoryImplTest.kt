package net.allm.challenge.repository.impl

import io.kotest.matchers.shouldBe
import io.mockk.*
import jakarta.persistence.EntityManager
import net.allm.challenge.repository.impl.entity.HospitalEntity
import net.allm.challenge.repository.impl.entity.PatientEntity
import net.allm.challenge.repository.impl.entity.VisitEntity
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class VisitRepositoryImplTest {

    private val em = mockk<EntityManager>()
    private val crud = mockk<VisitCrudRepository>()
    private val patientsCrud = mockk<PatientsCrudRepository>()

    private val tested = VisitRepositoryImpl(
            em = em,
            crud = crud,
            patientsCrudRepository = patientsCrud
    )

    @Test
    fun `creating a visit should fetch joined entities and persist the result`() {
        val hospitalId = 10L
        val hospital = HospitalEntity().also {
            it.id = hospitalId
            it.name = "hospital name"
        }

        val username = "username"
        val patient = PatientEntity().also {
            it.username = username
            it.id = 2L
            it.name = "name"
        }

        every {
            em.getReference(HospitalEntity::class.java, hospitalId)
        } answers { hospital }

        every {
            patientsCrud.findDistinctByUsername(username)
        } answers { patient }

        val dateTime = LocalDateTime.now()

        val generatedId = 100L
        val expectedToBeAfterSave = VisitEntity()
                .also {
                    it.id = generatedId
                    it.patient = patient
                    it.hospital = hospital
                    it.dateTime = dateTime
                }

        val savedEntity = slot<VisitEntity>()
        every {
            crud.save(capture(savedEntity))
        } answers {
            expectedToBeAfterSave
        }

        val saved = tested.createNewVisit(
                username = username,
                hospitalId = hospitalId,
                dateTime = dateTime
        )

        saved shouldBe expectedToBeAfterSave.toDomain()
        verify {
            em.getReference(HospitalEntity::class.java, hospitalId)
            patientsCrud.findDistinctByUsername(username)
            crud.save(savedEntity.captured)
        }

        confirmVerified(em, patientsCrud, crud)
    }

}