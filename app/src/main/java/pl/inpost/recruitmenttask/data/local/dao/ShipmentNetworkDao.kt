package pl.inpost.recruitmenttask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity

@Dao
interface ShipmentNetworkDao {
    @Query("Select * FROM shipments s ORDER BY s.status ASC, s.pickUpDate DESC, s.expiryDate DESC, s.storedDate DESC, s.number DESC")
    suspend fun getShipmentByOrder(): List<ShipmentNetworkEntity>
    @Insert
    suspend fun saveShipments(shipments: List<ShipmentNetworkEntity>)
    @Insert
    suspend fun saveSingleShipment(shipments: ShipmentNetworkEntity)
}
