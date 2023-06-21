package net.allm.challenge.repository.impl

import net.allm.challenge.repository.impl.entity.SecurityUserEntity
import org.springframework.data.repository.CrudRepository


internal interface SecurityUserCrudRepository: CrudRepository<SecurityUserEntity, String>