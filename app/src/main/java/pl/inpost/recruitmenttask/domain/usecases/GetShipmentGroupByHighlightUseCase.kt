package pl.inpost.recruitmenttask.domain.usecases

import android.util.Log
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import javax.inject.Inject

class GetShipmentGroupByHighlightUseCase @Inject constructor(
    private val shipmentNetworkRepository: ShipmentNetworkRepository
) {
    suspend fun groupingByHighlight(): List<ShipmentNetworkEntity> {
        val shipments = shipmentNetworkRepository.getShipments()
        Log.d("ShipmentByHighlight", "$shipments")
        return shipments
    }
}