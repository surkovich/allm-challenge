package net.allm.challenge.controller.impl

import net.allm.challenge.controller.api.NewVisitRequest
import net.allm.challenge.controller.api.VisitDto
import net.allm.challenge.service.api.VisitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1")
internal class VisitController @Autowired constructor(
        private val visitService: VisitService
) {

    @GetMapping("/visits")
    fun getUsersVisits(principal: Principal): List<VisitDto> =
            visitService.findUsersVisits(principal.name).toVisitDtos()

    @PostMapping("/visits")
    fun createVisit(
            principal: Principal,
            @RequestBody newVisitRequest: NewVisitRequest
    ): VisitDto =
            visitService.createNewVisit(
                    userName = principal.name,
                    dateTime = newVisitRequest.dateTime,
                    hospitalId = newVisitRequest.hospitalId
            ).toDto()

    @DeleteMapping("/visits/{id}")
    fun deleteVisit(
            principal: Principal,
            @PathVariable id: Long
    ) =
            visitService.deleteVisit(
                    id, principal.name
            )
}

