package fr.racomach.database.park

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object Parks : UUIDTable("parks") {
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp())
    val disabled = bool("disabled")
    val externalId = varchar("external_id", 100).uniqueIndex("unique_external_id")
    val address = text("address")
    val city = varchar("city", 255)
    val place = varchar("place", 100)
    val sheltered = bool("sheltered")
    val spot = integer("spot")
    val latitude = double("latitude")
    val longitude = double("longitude")
}