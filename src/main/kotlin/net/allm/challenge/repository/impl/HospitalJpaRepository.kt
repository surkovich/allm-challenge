package net.allm.challenge.repository.impl

import net.allm.challenge.repository.impl.entity.HospitalEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository


internal interface HospitalJpaRepository : PagingAndSortingRepository<HospitalEntity, Long> {
    fun findByNameIsContainingIgnoreCase(namePart: String, pageable: Pageable): List<HospitalEntity>
}