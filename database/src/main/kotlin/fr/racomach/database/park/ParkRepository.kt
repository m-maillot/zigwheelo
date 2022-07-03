package fr.racomach.database.park

import arrow.core.Either
import fr.racomach.database.CrudRepository
import fr.racomach.database.DatabaseFactory
import fr.racomach.database.park.Parks.address
import fr.racomach.database.park.Parks.city
import fr.racomach.database.park.Parks.disabled
import fr.racomach.database.park.Parks.externalId
import fr.racomach.database.park.Parks.latitude
import fr.racomach.database.park.Parks.longitude
import fr.racomach.database.park.Parks.place
import fr.racomach.database.park.Parks.sheltered
import fr.racomach.database.park.Parks.spot
import fr.racomach.database.utils.execAndMap
import fr.racomach.database.utils.upsert
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.time.Instant
import java.util.*
import kotlin.math.max

interface ParkRepository {
    suspend fun upsertParks(parks: List<ParkInput>): Either<Throwable, UpsertResult>

    suspend fun nearestParks(
        position: Position,
        distance: Int
    ): Either<Throwable, List<ParkOutput>>

    data class Position(
        val latitude: Double,
        val longitude: Double,
    )

    data class UpsertResult(
        val added: Long,
        val modified: Long,
        val removed: Long,
        val total: Long,
    )
}

class ParkDatabase(
    override val database: DatabaseFactory
) : ParkRepository,
    CrudRepository<ParkInput, ParkInput, ParkOutput>(database) {

    override val table = Parks

    override fun toDomain(row: ResultRow): ParkOutput {
        return ParkOutput(
            id = row[Parks.id].value.toParkId(),
            externalId = row[externalId],
            address = row[address],
            city = row[city],
            place = row[place],
            sheltered = row[sheltered],
            spot = row[spot],
            location = ParkOutput.Location(
                latitude = row[latitude],
                longitude = row[longitude],
            ),
        )
    }

    override fun toRow(statement: InsertStatement<Number>, insert: ParkInput) {
        statement[externalId] = insert.externalId
        statement[address] = insert.address
        statement[city] = insert.city
        statement[place] = insert.place
        statement[sheltered] = insert.sheltered
        statement[spot] = insert.spot
        statement[latitude] = insert.latitude
        statement[longitude] = insert.longitude
    }

    override fun match(id: UUID): SqlExpressionBuilder.() -> Op<Boolean> = {
        Parks.id eq id
    }

    override fun updateRow(statement: UpdateStatement, update: ParkInput) {
        statement[externalId] = update.externalId
        statement[address] = update.address
        statement[city] = update.city
        statement[place] = update.place
        statement[sheltered] = update.sheltered
        statement[spot] = update.spot
        statement[latitude] = update.latitude
        statement[longitude] = update.longitude
    }

    override suspend fun upsertParks(parks: List<ParkInput>) = Either.catch {
        database.dbQuery {
            val enabledCountBefore = count()
            Parks.deleteWhere { disabled eq true }
            Parks.update { it[disabled] = true }
            val modified = parks.map { park ->
                Parks.upsert(externalId) {
                    it[externalId] = park.externalId
                    it[address] = park.address
                    it[city] = park.city
                    it[place] = park.place
                    it[sheltered] = park.sheltered
                    it[spot] = park.spot
                    it[longitude] = park.longitude
                    it[latitude] = park.latitude
                    it[disabled] = false
                    it[updatedAt] = Instant.now()
                }.insertedCount
            }.sum()
            val enabledCountAfter = count()
            val disabledCount = count(true)
            ParkRepository.UpsertResult(
                max(enabledCountAfter - enabledCountBefore, 0),
                modified.toLong(),
                disabledCount,
                enabledCountAfter
            )
        }
    }

    override suspend fun nearestParks(position: ParkRepository.Position, distance: Int) =
        Either.catch {
            database.dbQuery {
                "SELECT * FROM (select *, (point(longitude,latitude) <@> point(${position.longitude},${position.latitude})) / 0.00062137 as distance FROM parks) parks WHERE distance < $distance ORDER BY distance".execAndMap { rs ->
                    ParkOutput(
                        id = rs.getString(Parks.id.name).toParkId(),
                        externalId = rs.getString(externalId.name),
                        address = rs.getString(address.name),
                        city = rs.getString(city.name),
                        place = rs.getString(place.name),
                        sheltered = rs.getBoolean(sheltered.name),
                        spot = rs.getInt(spot.name),
                        location = ParkOutput.Location(
                            latitude = rs.getDouble(latitude.name),
                            longitude = rs.getDouble(longitude.name),
                        )
                    )
                }
            }
        }

    private fun count(disabled: Boolean = false) =
        Parks.select { Parks.disabled eq disabled }.count()
}