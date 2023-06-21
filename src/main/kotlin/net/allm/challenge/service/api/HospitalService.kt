package net.allm.challenge.service.api

interface HospitalService {
    fun find(namePart: String, page: Int, size: Int): List<Hospital>
}