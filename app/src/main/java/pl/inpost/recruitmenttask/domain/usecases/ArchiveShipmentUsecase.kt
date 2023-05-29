package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import javax.inject.Inject

class ArchiveShipmentUseCase @Inject constructor(
    private val shipmentNetworkRepository: ShipmentNetworkRepository
) {
    suspend fun archive(entity: ShipmentNetworkEntity){
        entity.archived = true
        shipmentNetworkRepository.update(entity)
    }
}