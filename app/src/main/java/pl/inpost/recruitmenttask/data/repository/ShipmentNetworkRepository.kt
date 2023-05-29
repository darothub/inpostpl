package pl.inpost.recruitmenttask.data.repository

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.model.ShipmentNetwork

interface ShipmentNetworkRepository {
    fun getShipments(): LiveData<List<ShipmentNetworkEntity>>
    suspend fun getApiShipments()
    suspend fun update(shipments: ShipmentNetworkEntity)
}


