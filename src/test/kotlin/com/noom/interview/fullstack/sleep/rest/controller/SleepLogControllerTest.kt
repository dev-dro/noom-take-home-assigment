package com.noom.interview.fullstack.sleep.rest.controller

import com.ninjasquad.springmockk.MockkBean
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException
import com.noom.interview.fullstack.sleep.getSleepLog
import com.noom.interview.fullstack.sleep.getSleepLogsAverages
import com.noom.interview.fullstack.sleep.service.SleepLogService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

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

    @Test
    fun `should return 200 with the last night sleep log when asked`() {
        every { sleepLogService.findLastNightSleepLog("john") } returns getSleepLog()
        mvc.perform(MockMvcRequestBuilders.get("/sleep-log/last-night")
            .header("username", "john"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("""{
                    | "date": "2024-09-22",
                    | "startedSleep": "2024-09-21 23:00",
                    | "wokeUp": "2024-09-22 07:00",
                    | "minutesSlept": 480,
                    | "feltWhenWokeUp": "GOOD"
                    |}""".trimMargin()))
    }

    @Test
    fun `should return 404 when the last night sleep log is not found`() {
        every { sleepLogService.findLastNightSleepLog("john") } throws SleepLogNotFoundException()
        mvc.perform(MockMvcRequestBuilders.get("/sleep-log/last-night")
            .header("username", "john"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `should return 200 with the sleep logs averages for the last 30 days`() {
        every { sleepLogService.findSleepLogsAveragesFrom30Days("john") } returns
                getSleepLogsAverages(startDate = LocalDate.of(2024, 8, 23), endDate = LocalDate.of(2024, 9, 22))

        mvc.perform(
            MockMvcRequestBuilders.get("/sleep-log/30-days-averages")
                .header("username", "john"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("""{
                    | "startDate": "2024-08-23",
                    | "endDate": "2024-09-22",
                    | "averageMinutesSlept": 470.0,
                    | "averageStartedSleep": "22:50",
                    | "averageWokeUp": "06:50",
                    | "frequencyFeltWhenWokeUp": {
                    |     "GOOD": 10,
                    |     "BAD": 10,
                    |     "OK": 10
                    | }
                    |}""".trimMargin()))
    }

    @Test
    fun `should return 404 when there's no sleep log in 30 days to calculate the averages`() {
        every { sleepLogService.findSleepLogsAveragesFrom30Days("john") } throws SleepLogNotFoundException()
        mvc.perform(MockMvcRequestBuilders.get("/sleep-log/30-days-averages")
            .header("username", "john"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}