package net.allm.challenge.repository.impl

import net.allm.challenge.repository.impl.entity.PatientEntity
import org.springframework.data.repository.CrudRepository

internal interface PatientsCrudRepository : CrudRepository<PatientEntity, Long> {

    fun findDistinctByUsername(username: String): PatientEntity?
}