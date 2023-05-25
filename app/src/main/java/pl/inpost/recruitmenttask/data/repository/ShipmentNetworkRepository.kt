package pl.inpost.recruitmenttask.data.repository

import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.model.ShipmentNetwork

interface ShipmentNetworkRepository {
    suspend fun getShipments(): List<ShipmentNetworkEntity>
}


