package net.allm.challenge.repository.impl

import net.allm.challenge.repository.impl.entity.HospitalEntity
import net.allm.challenge.repository.impl.entity.PatientEntity
import net.allm.challenge.repository.impl.entity.VisitEntity
import net.allm.challenge.service.api.Hospital
import net.allm.challenge.service.api.Patient
import net.allm.challenge.service.api.Visit


/**
 * Currently added only manual mappers. Maybe in production code we can use some nice
 * automated objects mapper - like MapStruct in java, or whatever. On the other hand -
 * usage of manually written mappers in Kotlin is not so bad since it is very transparent
 * thanks to named parameters placeholders
 *
 * In other words, this is the small topic for discussion inside a team in case of real project
 */
internal fun HospitalEntity.toDomain(): Hospital =
        Hospital(
                id = this.id!!,
                name = this.name
        )

internal fun List<HospitalEntity>.toDomains() =
        this.map { it.toDomain() }

internal fun PatientEntity.toDomain(): Patient =
        Patient(
                id = this.id!!,
                name = this.name,
                username = this.username
        )

internal fun VisitEntity.toDomain() =
        Visit(
                id = this.id!!,
                patient = this.patient.toDomain(),
                hospital = this.hospital.toDomain(),
                dateTime = this.dateTime
        )

internal fun List<VisitEntity>.toVisitDomains() =
        this.map { it.toDomain() }
