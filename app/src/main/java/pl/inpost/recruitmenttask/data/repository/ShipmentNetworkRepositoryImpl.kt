package pl.inpost.recruitmenttask.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.model.toEntity
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class ShipmentNetworkRepositoryImpl @Inject constructor(
    private val api: ShipmentApi,
    private val shipmentNetworkDao: ShipmentNetworkDao
) : ShipmentNetworkRepository {

    override suspend fun getShipments(): List<ShipmentNetworkEntity> {
        val data = getDbShipments()
        return data.ifEmpty { getApiShipments() }
    }

    private suspend fun getDbShipments() = shipmentNetworkDao.getShipmentGroupingByHighlight()
    private suspend fun getApiShipments(): List<ShipmentNetworkEntity> {
        val shipments = api.getShipments()
        val entities = shipments.map {
            it.toEntity()
        }
        shipmentNetworkDao.saveShipments(entities)
        return entities
    }
}