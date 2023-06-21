package net.allm.challenge.controller.impl

import net.allm.challenge.controller.api.HospitalResponseDto
import net.allm.challenge.service.api.HospitalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


internal const val EMPTY_NAME_PART = ""
internal const val DEFAULT_PAGE = 0
internal const val DEFAULT_PAGE_SIZE = 20
internal const val DEFAULT_PAGE_SIZE_LIMIT = 20

@RestController
@RequestMapping("/api/v1")
internal class HospitalController @Autowired constructor(
        private val service: HospitalService,
        private val pageSizeLimit: Int = DEFAULT_PAGE_SIZE_LIMIT) {

    @GetMapping("/hospitals")
    fun getAllHospitals(
            @RequestParam
            namePart: String?,
            @RequestParam
            page: Int?,
            @RequestParam
            size: Int?
    ): List<HospitalResponseDto> =
            //TODO validate page and size or substitute with defaults
            service.find(
                    namePart = namePart ?: EMPTY_NAME_PART,
                    page = page ?: DEFAULT_PAGE,
                    size = pageSizeOrDefaultLimited(size)
            ).toHospitalDtos()

    private fun pageSizeOrDefaultLimited(size: Int?): Int {
        size ?: return DEFAULT_PAGE_SIZE

        return if (size > DEFAULT_PAGE_SIZE_LIMIT)
            DEFAULT_PAGE_SIZE_LIMIT
        else
            size
    }

}