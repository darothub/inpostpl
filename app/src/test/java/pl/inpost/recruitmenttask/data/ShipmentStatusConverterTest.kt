package pl.inpost.recruitmenttask.data

import androidx.room.Room
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import pl.inpost.recruitmenttask.data.local.ShipmentNetworkDatabase
import pl.inpost.recruitmenttask.data.local.ShipmentStatusConverter
import pl.inpost.recruitmenttask.data.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.model.toEntity
import pl.inpost.recruitmenttask.data.network.api.mockShipmentNetwork
import pl.inpost.recruitmenttask.util.getOrAwaitValue

class ShipmentStatusConverterTest {
    lateinit var converter: ShipmentStatusConverter

    @Before
    fun setupDatabase() {
        converter = ShipmentStatusConverter()
    }

    @Test
    fun toStatus() {
        val statusCreated = ShipmentStatus.CREATED
        val statusCreatedByConverter = converter.toStatus(statusCreated.ordinal)
        assertEquals(statusCreatedByConverter, statusCreated)
    }
    @Test
    fun fromStatus() {
        val ordinal = ShipmentStatus.CREATED.ordinal
        val ordinalCreatedByConverter = converter.fromStatus(ShipmentStatus.CREATED)
        assertEquals(ordinal, ordinalCreatedByConverter)
    }
}