package pl.inpost.recruitmenttask.data

import kotlinx.coroutines.delay
import pl.inpost.recruitmenttask.data.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.network.api.mockShipmentNetwork

class FakeShipmentApi: ShipmentApi {
    private var firstUse = true
    override suspend fun getShipments(): List<ShipmentNetwork> {
        delay(1000)
        return if (firstUse) {
            firstUse = false
            emptyList()
        } else {
            listOf(mockShipmentNetwork(), mockShipmentNetwork())
        }
    }
}