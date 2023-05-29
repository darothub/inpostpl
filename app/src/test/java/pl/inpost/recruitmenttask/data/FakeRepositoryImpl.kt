package pl.inpost.recruitmenttask.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.model.toEntity
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import pl.inpost.recruitmenttask.util.setState

class FakeRepositoryImpl(
    private val dao: ShipmentNetworkDao,
    private val api: ShipmentApi
): ShipmentNetworkRepository {
    override fun getShipments() = dao.getShipmentByOrder()

    override suspend fun getApiShipments() {
        if (dao.getCount() == 0) {
            val shipments = api.getShipments()
            val entities = shipments.map {
                it.toEntity()
            }
            (dao as FakeShipmentNetworkDaoImpl).dataBase.addAll(entities)
        }
    }

    override suspend fun update(shipments: ShipmentNetworkEntity) {
        dao.update(shipments)
    }
}

class FakeShipmentNetworkDaoImpl: ShipmentNetworkDao {
    val dataBase = mutableListOf<ShipmentNetworkEntity>()
    private val liveData = MutableLiveData<List<ShipmentNetworkEntity>>(emptyList())
    override fun getShipmentByOrder():LiveData<List<ShipmentNetworkEntity>> {
        liveData.setState { dataBase }
        return liveData
    }

    override suspend fun saveShipments(shipments: List<ShipmentNetworkEntity>) {
        dataBase.addAll(shipments)
    }

    override suspend fun saveShipment(shipments: ShipmentNetworkEntity) {
        dataBase.add(shipments)
    }

    override suspend fun update(shipments: ShipmentNetworkEntity) {
        val filtered = dataBase.filter { it.number == shipments.number }
        val index = dataBase.indexOf(filtered[0])
        dataBase[index] = shipments
    }

    override suspend fun getCount() = dataBase.size
}