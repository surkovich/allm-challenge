package net.allm.challenge.repository.impl

import net.allm.challenge.repository.api.SecurityUserRepository
import net.allm.challenge.repository.impl.entity.SecurityUserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Repository

@Repository
internal class SecurityUserRepositoryImpl @Autowired constructor(
        private val crud: SecurityUserCrudRepository
) : SecurityUserRepository {

    override fun getUserByUsername(username: String): User? {
        val entity = crud.findById(username)
        if (entity.isEmpty)
            return null
        if (entity.get().enabled != true)
            return null

        return entity.get().toUser()
    }

    /**
     * Placed it here. Not only because of coding in hurry,
     * but also because security-related code can/should be later moved to a separate
     * module or microservice
     */
    fun SecurityUserEntity.toUser(): User =
            User(
                    this.username,
                    this.password,
                    listOf()
            )

}