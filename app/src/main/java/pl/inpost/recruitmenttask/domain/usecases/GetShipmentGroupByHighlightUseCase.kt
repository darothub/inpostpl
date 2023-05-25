package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import javax.inject.Inject

class GetShipmentGroupByHighlightUseCase @Inject constructor(
    private val shipmentNetworkRepository: ShipmentNetworkRepository
) {
    suspend fun groupingByHighlight() {

    }
}