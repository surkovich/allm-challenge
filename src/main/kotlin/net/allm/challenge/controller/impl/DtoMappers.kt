package net.allm.challenge.controller.impl

import net.allm.challenge.controller.api.HospitalResponseDto
import net.allm.challenge.controller.api.VisitDto
import net.allm.challenge.service.api.Hospital
import net.allm.challenge.service.api.Visit


internal fun Hospital.toDto(): HospitalResponseDto =
        HospitalResponseDto(
                id = this.id,
                name = this.name
        )

internal fun List<Hospital>.toHospitalDtos(): List<HospitalResponseDto> =
        this.map { it.toDto() }

internal fun Visit.toDto(): VisitDto =
        VisitDto(
                id = this.id!!,
                hospitalName = this.hospital.name,
                dateTime = this.dateTime
        )

internal fun List<Visit>.toVisitDtos(): List<VisitDto> =
        this.map { it.toDto() }