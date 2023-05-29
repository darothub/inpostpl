package pl.inpost.recruitmenttask.domain.usecases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import javax.inject.Inject

class GetShipmentGroupByHighlightUseCase @Inject constructor(
    private val shipmentNetworkRepository: ShipmentNetworkRepository
) {
    fun getGroupedShipmentsByHighlight(): LiveData<Pair<List<ShipmentNetworkEntity>, List<ShipmentNetworkEntity>>> {
        val allShipments = shipmentNetworkRepository.getShipments()
        return allShipments.map { shipments ->
            val highlightedShipments = shipments.filter { it.operations.highlight }
            val notHighlightedShipments = shipments.filterNot { it.operations.highlight }
            Pair(highlightedShipments, notHighlightedShipments)
        }
    }

    suspend fun syncShipments() {
        shipmentNetworkRepository.getApiShipments()
    }

}