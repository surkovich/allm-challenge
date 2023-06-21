package net.allm.challenge.repository.impl

import net.allm.challenge.repository.api.HospitalRepository
import net.allm.challenge.service.api.Hospital
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository


@Repository
internal class HospitalRepositoryImpl(private val jpaRepository: HospitalJpaRepository) : HospitalRepository {

    override fun find(namePart: String, page: Int, size: Int): List<Hospital> =
            jpaRepository.findByNameIsContainingIgnoreCase(namePart, PageRequest.of(page, size))
                    .toDomains()


}