package pl.inpost.recruitmenttask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity

@Dao
interface ShipmentNetworkDao {
    @Query("Select * FROM shipments s GROUP BY s.highlight ORDER BY s.status, s.pickUpDate, s.expiryDate, s.storedDate, s.number")
    suspend fun getShipmentGroupingByHighlight(): List<ShipmentNetworkEntity>
    @Insert
    suspend fun saveShipments(shipments: List<ShipmentNetworkEntity>)
    @Insert
    suspend fun saveSingleShipment(shipments: ShipmentNetworkEntity)
}
