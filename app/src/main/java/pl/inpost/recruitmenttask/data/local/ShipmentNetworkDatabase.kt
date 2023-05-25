package pl.inpost.recruitmenttask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.inpost.recruitmenttask.data.local.dao.EventLogDao
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.local.entities.EventLogEntity
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity

@Database(entities = [ShipmentNetworkEntity::class, EventLogEntity::class], version = 1, exportSchema = false)
abstract class ShipmentNetworkDatabase: RoomDatabase() {
    abstract fun getShipmentNetworkDao(): ShipmentNetworkDao
    abstract fun getEventLogDao(): EventLogDao
}