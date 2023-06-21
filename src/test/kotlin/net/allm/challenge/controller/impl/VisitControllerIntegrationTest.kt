package net.allm.challenge.controller.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import net.allm.challenge.controller.api.VisitDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ExtendWith(SpringExtension::class)
@ContextConfiguration
@SpringBootTest
@WebAppConfiguration
internal class VisitControllerIntegrationTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    private var mvc: MockMvc? = null

    private val mapper = ObjectMapper()
            .registerModule(KotlinModule.Builder()
                    .withReflectionCacheSize(512)
                    .configure(KotlinFeature.NullToEmptyCollection, false)
                    .configure(KotlinFeature.NullToEmptyMap, false)
                    .configure(KotlinFeature.NullIsSameAsDefault, false)
                    .configure(KotlinFeature.StrictNullChecks, false)
                    .build())
            .registerModule(JavaTimeModule())

    @WithMockUser(value = "leif")
    @Test
    fun `create, check that created, delete, check that visit doesn't exist after delete`() {
        val dateTime = "2023-07-12T12:00:00"

        val requestBody = """
{
    "dateTime": "$dateTime",
    "hospitalId": 1
}
            """.trimIndent()
        val expectedVisit = """
{
    "hospitalName": "Flowerhill General Hospital",
    "dateTime": "$dateTime"
}
        """.trimIndent()

        val createVisitResponse = mvc!!.post("/api/v1/visits") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(expectedVisit) }
        }.andReturn().response.contentAsString

        val createdVisit = mapper
                .readValue(createVisitResponse, VisitDto::class.java)

        val allMyVisits: List<VisitDto> = mapper.readValue<List<VisitDto>>(
                mvc!!.get("/api/v1/visits") {}.andReturn().response.contentAsString
        )

        allMyVisits.toSet() shouldContain createdVisit

        mvc!!.delete("/api/v1/visits/${createdVisit.id}")

        val allVisitsAfterDelete: List<VisitDto> = mapper.readValue<List<VisitDto>>(
                mvc!!.get("/api/v1/visits") {}.andReturn().response.contentAsString
        )

        allVisitsAfterDelete shouldNotContain createdVisit
    }


    @BeforeEach
    fun setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(springSecurity())
                .build()
    }

}