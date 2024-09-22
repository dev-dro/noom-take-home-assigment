package com.noom.interview.fullstack.sleep.rest.controller

import com.ninjasquad.springmockk.MockkBean
import com.noom.interview.fullstack.sleep.getSleepLog
import com.noom.interview.fullstack.sleep.service.SleepLogService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [SleepLogController::class])
class SleepLogControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockkBean
    lateinit var sleepLogService: SleepLogService

    @Test
    fun `should return 201 with the saved id when a new sleep log is created`() {
        val json = """{ 
            |   "startedSleep": "2024-09-21 23:00:00", 
            |   "wokeUp": "2024-09-22 07:00:00", 
            |   "feltWhenWokeUp": "GOOD" 
            |}""".trimMargin()
        val sleepLog = getSleepLog()

        every { sleepLogService.createSleepLog(sleepLog) } returns 1L

        mvc.perform(MockMvcRequestBuilders.post("/sleep-log")
            .header("username", "john")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().json("""{"id":  1}"""))
    }
}