package net.allm.challenge.repository.impl

import net.allm.challenge.repository.impl.entity.PatientEntity
import net.allm.challenge.repository.impl.entity.VisitEntity
import org.springframework.data.repository.CrudRepository

internal interface VisitCrudRepository : CrudRepository<VisitEntity, Long> {

    fun findAllByPatient(patientEntity: PatientEntity): List<VisitEntity>

}