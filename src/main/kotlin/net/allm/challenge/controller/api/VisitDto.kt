package net.allm.challenge.controller.api

import java.time.LocalDateTime

data class VisitDto(
        val id: Long,
        val hospitalName: String,
        val dateTime: LocalDateTime
)