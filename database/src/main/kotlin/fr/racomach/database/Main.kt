package fr.racomach.database

import com.zaxxer.hikari.HikariConfig
import java.util.*

fun HikariConfig.ajmPostgresConfig(
    jdbcurl: String,
    userName: String,
    passwordInput: String,
) =
    this.apply {
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 3
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        jdbcUrl = jdbcurl
        username = userName
        password = passwordInput
        isAutoCommit = false
        connectionTimeout = 1000
    }

fun HikariConfig.ajmH2Config(uniqueId: String = UUID.randomUUID().toString()) =
    this.apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:test-$uniqueId"
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }