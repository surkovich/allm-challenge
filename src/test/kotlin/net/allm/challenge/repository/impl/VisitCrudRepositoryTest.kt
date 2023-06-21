package net.allm.challenge.repository.impl

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class VisitCrudRepositoryTest @Autowired constructor(
        val repository: VisitCrudRepository
) {

    @Test
    fun `findById should return joined entities fetched`() {
        //In this realization I use eager initialization for joined entities.
        //Maybe if business process will become more complicated it will be reconsidered
        //This why I fix this functionality with test
        val entity = repository.findById(1)
        entity.get().toDomain()
    }

}