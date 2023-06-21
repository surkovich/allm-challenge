package net.allm.challenge.repository.impl

import net.allm.challenge.repository.api.PatientRepository
import net.allm.challenge.service.api.Patient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
internal class PatientRepositoryImpl @Autowired constructor(
        private val crudRepository: PatientsCrudRepository
): PatientRepository {

    override fun findByUsername(username: String): Patient? =
            crudRepository.findDistinctByUsername(username)?.toDomain()



}