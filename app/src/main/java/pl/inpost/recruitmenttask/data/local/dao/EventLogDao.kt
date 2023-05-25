package pl.inpost.recruitmenttask.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import pl.inpost.recruitmenttask.data.local.entities.EventLogEntity

@Dao
interface EventLogDao {
    @Query("SELECT * FROM EVENT_LOGS WHERE shipmentNumber =:shipmentNumber")
    suspend fun getEventLog(shipmentNumber: String): List<EventLogEntity>
}