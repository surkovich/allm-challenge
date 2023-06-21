package net.allm.challenge.repository.api

import net.allm.challenge.service.api.Hospital

interface HospitalRepository {
    fun find(namePart: String, page: Int, size: Int): List<Hospital>
}