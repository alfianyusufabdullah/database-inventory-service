package alfianyusufabdullah.databaseservice.repository

import alfianyusufabdullah.databaseservice.database.InventoryTable
import alfianyusufabdullah.databaseservice.database.dbQuery
import alfianyusufabdullah.databaseservice.model.Inventory
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class DatabaseRepository {

    suspend fun getAllInventory(): HashMap<String, Inventory> {
        val inventory = hashMapOf<String, Inventory>()
        dbQuery {
            InventoryTable.selectAll().forEach {
                inventory[it[InventoryTable.serialnumber]] = Inventory(
                    serialNumber = it[InventoryTable.serialnumber],
                    name = it[InventoryTable.name],
                    distribution = it[InventoryTable.distribution],
                    price = it[InventoryTable.price].toInt()
                )
            }
        }
        return inventory
    }

    suspend fun insertInventory(inventory: Inventory): Boolean {
        return dbQuery {
            try {
                InventoryTable.insert {
                    it[serialnumber] = inventory.serialNumber
                    it[name] = inventory.name
                    it[distribution] = inventory.distribution
                    it[price] = inventory.price
                }
                true
            } catch (e: JdbcSQLIntegrityConstraintViolationException) {
                println(e.localizedMessage)
                false
            } catch (e: ExposedSQLException) {
                println(e.localizedMessage)
                false
            }
        }
    }

    suspend fun deleteInventory(id: String): Int {
        return dbQuery {
            InventoryTable.deleteWhere { InventoryTable.serialnumber eq id }
        }
    }

    suspend fun updateInventory(inventory: Inventory): Int {
        return dbQuery {
            InventoryTable.update({ InventoryTable.serialnumber eq inventory.serialNumber }) {
                it[name] = inventory.name
                it[distribution] = inventory.distribution
                it[price] = inventory.price
            }
        }
    }
}