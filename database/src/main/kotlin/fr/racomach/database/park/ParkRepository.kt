package fr.racomach.database.park

import arrow.core.Either
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoIterable
import com.mongodb.client.model.geojson.Point
import com.mongodb.client.model.geojson.Position
import org.litote.kmongo.*
import org.litote.kmongo.util.KMongoUtil

interface ParkRepository {
    fun upsertParks(parks: List<ParkInput>): Either<Throwable, UpsertResult>

    fun nearestParks(position: Position, distance: Int): Either<Throwable, MongoIterable<ParkOutput>>

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

class ParkDatabase(db: MongoDatabase) : ParkRepository {

    private val collection = db.getCollection<ParkEntity>("park_entity")

    override fun upsertParks(parks: List<ParkInput>) = Either.catch {
        (listOf(updateMany<ParkEntity>(EMPTY_BSON, setValue(ParkEntity::valid, false))) + createUpsertBulk(parks))
            .let { collection.bulkWrite(it) }
            .let {
                ParkRepository.UpsertResult(
                    it.insertedCount.toLong(),
                    it.modifiedCount.toLong(),
                    count(false),
                    count(true)
                )
            }
            .also { collection.createIndex(geo2dsphere(ParkEntity::location)) }
    }

    override fun nearestParks(position: ParkRepository.Position, distance: Int) = Either.catch {
        collection.find(
            ParkEntity::location.near(
                geometry = Point(Position(position.longitude, position.latitude)),
                maxDistance = distance.toDouble()
            )
        ).map { it.toOutput() }
    }

    private fun count(valid: Boolean) = collection.countDocuments(ParkEntity::valid eq valid)

    private fun createUpsertBulk(parks: List<ParkInput>) =
        parks.map { updateOne<ParkEntity>(ParkEntity::externalId eq it.externalId, it.toBsonEntity(), upsert()) }

    private fun ParkInput.toBsonEntity() = KMongoUtil.toBsonModifier(
        ParkEntity(
            externalId, address, city, place, sheltered, spot, ParkEntity.Location(listOf(longitude, latitude)), true
        ),
        false,
    )

    private fun ParkEntity.toOutput() = ParkOutput(
        id.toString(),
        externalId,
        address,
        city,
        place,
        sheltered,
        spot,
        ParkOutput.Location(location.coordinates[0], location.coordinates[1])
    )

}