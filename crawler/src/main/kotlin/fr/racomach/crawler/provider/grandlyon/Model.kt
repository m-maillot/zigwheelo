package fr.racomach.crawler.provider.grandlyon

import kotlinx.serialization.Serializable

@Serializable
data class BikeParksResponse(
    val features: List<Feature>
) {
    @Serializable
    data class Feature(
        val properties: Properties,
        val geometry: Geometry
    ) {
        @Serializable
        data class Properties(
            val nom: String,
            val adresse: String,
            val commune: String,
            val avancement: String,
            val gestionnaire: String,
            val mobiliervelo: String,
            val localisation: String,
            val abrite: String,
            val duree: String,
            val capacite: Int? = null,
            val pole: String,
            val gid: Int
        )

        @Serializable
        data class Geometry(
            val type: String,
            val coordinates: List<Double>
        )
    }
}