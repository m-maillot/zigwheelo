package fr.racomach.crawler

import fr.racomach.crawler.event.UpdateParks
import fr.racomach.crawler.provider.grandlyon.GrandLyonApi
import fr.racomach.database.connectDabatase
import fr.racomach.database.park.ParkDatabase
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.util.logging.Logger

suspend fun main() {
    val logger = Logger.getLogger("CRAWLER")
    val api = GrandLyonApi(HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    })

    val database = connectDabatase(
        System.getenv("MONGODB_URI"),
        System.getenv("MONGODB_DATABASE"),
    )

    val parkRepository = ParkDatabase(database)

    UpdateParks(api, parkRepository).run().fold({
        logger.severe { "Error during parking crawl: ${it.localizedMessage} (${it.cause})" }
    }, {
        logger.info { "Parking crawling done ! ${it.added} added / ${it.modified} modified / $${it.removed} removed / ${it.total} total" }
    })
}
