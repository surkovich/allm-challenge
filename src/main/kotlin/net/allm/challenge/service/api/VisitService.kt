package net.allm.challenge.service.api

import java.time.LocalDateTime

interface VisitService {

    fun findUsersVisits(userName: String): List<Visit>

    fun createNewVisit(userName: String, hospitalId: Long, dateTime: LocalDateTime): Visit

    fun deleteVisit(visitId: Long, userName: String)
}