package fr.racomach.crawler.provider.grandlyon

import arrow.core.Either
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class GrandLyonApi(private val client: HttpClient) {
    private val baseUrl = "https://download.data.grandlyon.com/wfs/"

    suspend fun bikeParks() = Either.catch {
        client.get("${baseUrl}grandlyon?SERVICE=WFS&VERSION=2.0.0&request=GetFeature&typename=pvo_patrimoine_voirie.pvostationnementvelo&outputFormat=application/json;%20subtype=geojson&SRSNAME=EPSG:4171&startIndex=0")
            .body<BikeParksResponse>()
    }
}