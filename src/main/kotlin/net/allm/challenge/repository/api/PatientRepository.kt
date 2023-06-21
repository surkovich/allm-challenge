package net.allm.challenge.repository.api

import net.allm.challenge.service.api.Patient

interface PatientRepository {
    fun findByUsername(username: String): Patient?
}