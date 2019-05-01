package alfianyusufabdullah.databaseservice

import alfianyusufabdullah.databaseservice.database.DatabaseConfiguration
import alfianyusufabdullah.databaseservice.repository.DatabaseRepository
import alfianyusufabdullah.databaseservice.database.InventoryTable
import alfianyusufabdullah.databaseservice.rest.widget
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.gson.*
import io.ktor.features.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    Database.connect(DatabaseConfiguration.hikariConfigDataSource())
    transaction {
        create(InventoryTable)
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(Routing) {
        widget(DatabaseRepository())
    }
}