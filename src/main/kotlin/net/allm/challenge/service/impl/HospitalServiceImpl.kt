package net.allm.challenge.service.impl

import net.allm.challenge.repository.api.HospitalRepository
import net.allm.challenge.service.api.Hospital
import net.allm.challenge.service.api.HospitalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class HospitalServiceImpl @Autowired constructor(private val repository: HospitalRepository): HospitalService {

    override fun find(namePart: String, page: Int, size: Int): List<Hospital> =
            repository.find(namePart, page, size)

}