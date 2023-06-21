package net.allm.challenge.service.api

import java.time.LocalDateTime

data class Visit(
        val id: Long,
        val patient: Patient,
        val hospital: Hospital,
        val dateTime: LocalDateTime
)