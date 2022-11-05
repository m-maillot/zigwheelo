package fr.racomach

import fr.racomach.server.plugins.configureRouting
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }

    @Test
    fun localTime() {
        @Serializable
        data class Test(val time: LocalTime)
        val time = Test(LocalTime(hour = 12, minute = 13))
        val json = Json.encodeToString(Test.serializer(), time)
        println("Time: $json")
        val time2 = Json.decodeFromString(Test.serializer(), "{\"time\":\"12:13\"}")
        println("Time2: $time2")
        assertEquals(time.toString(), "21")
    }
}