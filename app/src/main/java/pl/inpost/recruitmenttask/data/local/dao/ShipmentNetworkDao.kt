package pl.inpost.recruitmenttask.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity

@Dao
interface ShipmentNetworkDao {
    @Query("Select * FROM shipments s WHERE s.archived = 0  ORDER BY s.status ASC, s.pickUpDate DESC, s.expiryDate DESC, s.storedDate DESC, s.number ASC")
    fun getShipmentByOrder(): LiveData<List<ShipmentNetworkEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveShipments(shipments: List<ShipmentNetworkEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveShipment(shipments: ShipmentNetworkEntity)
    @Update
    suspend fun update(shipments: ShipmentNetworkEntity)

    @Query("SELECT COUNT(*) FROM shipments")
    suspend fun getCount(): Int
}
