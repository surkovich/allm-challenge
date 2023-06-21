package net.allm.challenge.service.impl

import net.allm.challenge.repository.api.PatientRepository
import net.allm.challenge.repository.api.VisitRepository
import net.allm.challenge.service.api.Visit
import net.allm.challenge.service.api.VisitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VisitServiceImpl @Autowired constructor(
        private val repository: VisitRepository,
        private val patientRepository: PatientRepository
): VisitService {

    //TODO find only upcoming visits?..
    override fun findUsersVisits(userName: String): List<Visit> =
        repository.findByUserName(userName)

    override fun createNewVisit(userName: String, hospitalId: Long, dateTime: LocalDateTime) =
            //TODO - in case of production we probably should also limit upcoming visits amount for each patient
            //This functionality will be for service layer
            repository.createNewVisit(
                    username = userName,
                    hospitalId = hospitalId,
                    dateTime = dateTime
            )

    override fun deleteVisit(visitId: Long, userName: String) {
        //All logic below can be moved to repository layer
        //Placed in service because we can have some more complicated logic in case of
        //access violation, not found patient, etc.
        //
        //Of course in current case we could do everything in a single query on a repository layer

        val patient = patientRepository.findByUsername(userName)
        val visit = repository.findById(visitId)
        if (visit == null || patient == null || visit.patient.id != patient.id)
            return

        repository.deleteById(visitId)
    }

}