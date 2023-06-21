package net.allm.challenge.repository.impl.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "security_users")
internal class SecurityUserEntity {
    @Id
    @Column(name = "username")
    lateinit var username: String
    @Column(name = "password")
    lateinit var password: String
    @Column(name = "enabled")
    var enabled: Boolean? = false
}
