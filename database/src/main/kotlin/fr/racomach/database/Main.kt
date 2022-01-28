package fr.racomach.database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoDatabase
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo

fun connectDabatase(connectionString: String, databaseName: String): MongoDatabase =
    KMongo.createClient(
        MongoClientSettings.builder()
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(ConnectionString(connectionString))
            .build()
    ).getDatabase(databaseName)