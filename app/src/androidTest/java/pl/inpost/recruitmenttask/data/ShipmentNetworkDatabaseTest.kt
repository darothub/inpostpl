package pl.inpost.recruitmenttask.data

import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.inpost.recruitmenttask.data.local.ShipmentNetworkDatabase
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.model.toEntity
import pl.inpost.recruitmenttask.data.network.api.mockShipmentNetwork
import pl.inpost.recruitmenttask.util.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
@SmallTest
class ShipmentNetworkDatabaseTest {

    private lateinit var database: ShipmentNetworkDatabase
    private lateinit var shipmentNetworkDao: ShipmentNetworkDao
    private val context = InstrumentationRegistry.getInstrumentation().context


    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            context,
            ShipmentNetworkDatabase::class.java
        ).allowMainThreadQueries().build()

        shipmentNetworkDao = database.getShipmentNetworkDao()
    }

    @Test
    fun testInsertShipmentNetworkInDatabase() = runBlocking {
        val sample = mockShipmentNetwork()
        shipmentNetworkDao.saveShipment(sample.toEntity())

        val shipmentLiveData = shipmentNetworkDao.getShipmentByOrder()
        val data = shipmentLiveData.getOrAwaitValue()
        assert(data.isNotEmpty())
    }
    @Test
    fun testShipmentStatusConverterReturnsRightOrdinalValueForShipmentStatus() = runBlocking {
        val sample = mockShipmentNetwork()
        shipmentNetworkDao.saveShipment(sample.toEntity())
        val shipmentLiveData = shipmentNetworkDao.getShipmentByOrder()
        val dataList = shipmentLiveData.getOrAwaitValue()
        val data = dataList[0]
        assertEquals(data.status.ordinal, 7)
    }
    @After
    fun closeDatabase() {
        database.close()
    }
}