package net.allm.challenge.controller.api

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class NewVisitRequest(
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        val dateTime: LocalDateTime,
        val hospitalId: Long
)