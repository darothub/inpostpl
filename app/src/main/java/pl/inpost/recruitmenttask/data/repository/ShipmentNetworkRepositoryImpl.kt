package pl.inpost.recruitmenttask.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
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

    override fun getShipments(): LiveData<List<ShipmentNetworkEntity>> = shipmentNetworkDao.getShipmentByOrder()

    override suspend fun update(shipments: ShipmentNetworkEntity) {
        shipmentNetworkDao.update(shipments)
    }
    override suspend fun getApiShipments() {
        if (shipmentNetworkDao.getCount() == 0) {
            val shipments = api.getShipments()
            val entities = shipments.map {
                it.toEntity()
            }
            shipmentNetworkDao.saveShipments(entities)
        }
    }
}