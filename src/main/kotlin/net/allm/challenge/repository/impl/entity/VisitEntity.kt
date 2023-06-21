package net.allm.challenge.repository.impl.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "visits")
internal class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    lateinit var hospital: HospitalEntity

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    lateinit var patient: PatientEntity

    @Column(name = "datetime", columnDefinition = "TIMESTAMP")
    lateinit var dateTime: LocalDateTime

}