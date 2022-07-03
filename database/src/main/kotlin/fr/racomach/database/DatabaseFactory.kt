package fr.racomach.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import fr.racomach.database.park.Parks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory(config: HikariConfig) {

    init {
        Database.connect(HikariDataSource(config))

        transaction {
            if (config.driverClassName == "org.postgresql.Driver") {
                with(TransactionManager.current()) {
                    exec("CREATE EXTENSION IF NOT EXISTS cube")
                    exec("CREATE EXTENSION IF NOT EXISTS earthdistance")
                    commit()
                }
            }
            SchemaUtils.create(Parks)
        }
    }

    suspend fun <T> dbQuery(
        block: () -> T
    ): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}
