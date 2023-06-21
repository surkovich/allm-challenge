package net.allm.challenge.repository.impl.entity

import jakarta.persistence.*


@Entity
@Table(name = "patients")
internal class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "username", nullable = false)
    lateinit var username: String

}
