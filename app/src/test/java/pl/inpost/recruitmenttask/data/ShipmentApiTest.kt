package pl.inpost.recruitmenttask.data

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import pl.inpost.recruitmenttask.data.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi

class ShipmentApiTest {
    lateinit var shipmentApi: ShipmentApi

    @Before
    fun setUp() {
        shipmentApi = FakeShipmentApi()
    }
    @Test
    fun testReturnsEmptyListOnFirstCall() = runBlocking {
        val data = shipmentApi.getShipments()
        assert(data.isEmpty())
    }
    @Test
    fun testReturnsANonEmptyListOnRefreshCall() = runBlocking {
        var data = emptyList<ShipmentNetwork>()
        repeat(2) {
            data = shipmentApi.getShipments()
        }
        assert(data.isNotEmpty())
    }
}