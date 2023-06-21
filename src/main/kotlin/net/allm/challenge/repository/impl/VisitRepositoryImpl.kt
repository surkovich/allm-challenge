package net.allm.challenge.repository.impl

import jakarta.persistence.EntityManager
import net.allm.challenge.controller.impl.toVisitDtos
import net.allm.challenge.repository.api.PatientRepository
import net.allm.challenge.repository.api.VisitRepository
import net.allm.challenge.repository.impl.entity.HospitalEntity
import net.allm.challenge.repository.impl.entity.VisitEntity
import net.allm.challenge.service.api.Visit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Repository
internal class VisitRepositoryImpl @Autowired constructor(
        private val crud: VisitCrudRepository,
        private val patientsCrudRepository: PatientsCrudRepository,
        private val em: EntityManager
) : VisitRepository {
    override fun findByUserName(username: String): List<Visit> {
        val patient = patientsCrudRepository.findDistinctByUsername(username)
        return if (patient == null)
            emptyList()
        else
            crud.findAllByPatient(patient).toVisitDomains()
    }

    override fun createNewVisit(username: String, hospitalId: Long, dateTime: LocalDateTime): Visit {
        val hospital = em.getReference(HospitalEntity::class.java, hospitalId)
        val patient = patientsCrudRepository.findDistinctByUsername(username)
        val visit = VisitEntity().also {
            it.patient = patient!!
            it.hospital = hospital!!
            it.dateTime = dateTime
        }
        return crud.save(visit).toDomain()
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun findById(id: Long): Visit? =
            crud.findById(id).getOrNull()?.toDomain()

    override fun deleteById(id: Long) =
            crud.deleteById(id)

}