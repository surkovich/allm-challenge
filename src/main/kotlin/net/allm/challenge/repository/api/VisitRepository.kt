package net.allm.challenge.repository.api

import net.allm.challenge.service.api.Visit
import java.time.LocalDateTime

interface VisitRepository {

    fun findByUserName(username: String): List<Visit>

    fun createNewVisit(username: String, hospitalId: Long, dateTime: LocalDateTime): Visit

    fun findById(id: Long): Visit?

    fun deleteById(id: Long)
}