package net.allm.challenge.service.impl

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.allm.challenge.repository.api.PatientRepository
import net.allm.challenge.repository.api.VisitRepository
import net.allm.challenge.service.api.Hospital
import net.allm.challenge.service.api.Patient
import net.allm.challenge.service.api.Visit
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class VisitServiceImplTest {

    private val visitRepository = mockk<VisitRepository>()
    private val patientRepository = mockk<PatientRepository>()
    private val tested = VisitServiceImpl(visitRepository, patientRepository)

    @Test
    fun `attempt to delete visit of another user should not lead to any database modification`() {

        val visitId = 1L
        val dateTime = LocalDateTime.now()
        val existingVisit = Visit(
                id = visitId,
                patient = Patient(
                        id = 2L,
                        name = "name",
                        username = "username"
                ),
                hospital = Hospital(
                        id = 4,
                        name = "hname"
                ),
                dateTime = dateTime
        )
        every {
            visitRepository.findById(visitId)
        } answers {
            existingVisit
        }

        val anotherUsername = "another"
        every {
            patientRepository.findByUsername(anotherUsername)
        } answers {
            Patient(
                    id = 20L,
                    name = "other",
                    username = anotherUsername
            )
        }

        tested.deleteVisit(visitId, anotherUsername)

        verify {
            visitRepository.findById(visitId)
            patientRepository.findByUsername(anotherUsername)
        }

        confirmVerified(visitRepository, patientRepository)
    }

    @Test
    fun `when patient by username matches requesting user then delete should be performed`() {

        val visitId = 1L
        val dateTime = LocalDateTime.now()
        val existingVisit = Visit(
                id = visitId,
                patient = Patient(
                        id = 2L,
                        name = "name",
                        username = "username"
                ),
                hospital = Hospital(
                        id = 4,
                        name = "hname"
                ),
                dateTime = dateTime
        )
        every {
            visitRepository.findById(visitId)
        } answers {
            existingVisit
        }

        every {
            patientRepository.findByUsername(existingVisit.patient.username)
        } answers {
            existingVisit.patient
        }

        every { visitRepository.deleteById(visitId) }.returns(Unit)

        tested.deleteVisit(visitId, existingVisit.patient.username)

        verify {
            visitRepository.findById(visitId)
            patientRepository.findByUsername(existingVisit.patient.username)
            visitRepository.deleteById(visitId)
        }

        confirmVerified(visitRepository, patientRepository)
    }

    @Test
    fun `WHEN patien for user name is not found then no delete should be performed`() {
        val visitId = 1L
        val dateTime = LocalDateTime.now()
        val existingVisit = Visit(
                id = visitId,
                patient = Patient(
                        id = 2L,
                        name = "name",
                        username = "username"
                ),
                hospital = Hospital(
                        id = 4,
                        name = "hname"
                ),
                dateTime = dateTime
        )
        every {
            visitRepository.findById(visitId)
        } answers {
            existingVisit
        }

        val anotherUsername = "another"
        every {
            patientRepository.findByUsername(anotherUsername)
        } answers {
            null
        }

        tested.deleteVisit(visitId, anotherUsername)

        verify {
            visitRepository.findById(visitId)
            patientRepository.findByUsername(anotherUsername)
        }

        confirmVerified(visitRepository, patientRepository)
    }

}