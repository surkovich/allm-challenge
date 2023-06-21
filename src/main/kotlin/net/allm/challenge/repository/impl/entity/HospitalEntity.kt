package net.allm.challenge.repository.impl.entity

import jakarta.persistence.*


@Entity
@Table(name = "HOSPITALS")
internal class HospitalEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        open var id: Long? = null
        lateinit var name: String
}