package fr.racomach.database

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.util.*

abstract class CrudRepository<INSERT, UPDATE, DOMAIN>(
    open val database: DatabaseFactory,
) {
    abstract val table: UUIDTable
    abstract fun toDomain(row: ResultRow): DOMAIN
    abstract fun toRow(statement: InsertStatement<Number>, insert: INSERT)
    abstract fun match(id: UUID): SqlExpressionBuilder.() -> Op<Boolean>
    abstract fun updateRow(statement: UpdateStatement, update: UPDATE)

    /**
     * Get a particular record by its ID.
     *
     * @param id The ID of the record to retrieve.
     * @return The domain object which corresponds to the specified ID if operation was successful or null otherwise.
     */
    suspend fun get(id: UUID): DOMAIN? = database.dbQuery {
        table.select(where = match(id)).singleOrNull()?.let { toDomain(it) }
    }

    /**
     * Simply retrieves all records.
     */
    suspend fun getAll(): List<DOMAIN> = database.dbQuery {
        table.selectAll().map { toDomain(it) }
    }

    /**
     * Creates a new record.
     *
     * @param domain The object to be created in its domain form.
     * @return The newly created record if operation was successful or null otherwise.
     */
    suspend fun create(domain: INSERT): DOMAIN? = database.dbQuery {
        table.insert { toRow(it, domain) }.let {
            it.resultedValues?.singleOrNull()?.let { toDomain(it) }
        }
    }

    /**
     * Updates an existing record.
     *
     * @param update The domain object which will be updated.
     * @return The freshly updated domain object if operation was successful or null otherwise.
     */
    suspend fun update(id: UUID, update: UPDATE): DOMAIN? = database.dbQuery {
        table.update(where = match(id), body = { updateRow(it, update) })
    }.let {
        if (it == 0) null else get(id)
    }

    /**
     * Deletes the single record which corresponds to the specified ID.
     *
     * @param id The ID of the record to be deleted.
     * @return The ID of the deleted record if operation was successful or null otherwise.
     */
    suspend fun delete(id: UUID): UUID? = database.dbQuery {
        table.deleteWhere(op = match(id)).let {
            if (it == 0) null else id
        }
    }
}