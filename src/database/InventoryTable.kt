package alfianyusufabdullah.databaseservice.database

import org.jetbrains.exposed.sql.Table

object InventoryTable : Table() {
    val serialnumber = varchar("serialnumber", 255).primaryKey()
    val name = varchar("name", 255)
    val distribution = varchar("dist", 255)
    val price = integer("price")
}