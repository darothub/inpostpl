package pl.inpost.recruitmenttask.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.recruitmenttask.data.FakeRepositoryImpl
import pl.inpost.recruitmenttask.data.FakeShipmentApi
import pl.inpost.recruitmenttask.data.FakeShipmentNetworkDaoImpl
import pl.inpost.recruitmenttask.data.MainCoroutineRule
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.model.toEntity
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.network.api.mockShipmentNetwork
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import pl.inpost.recruitmenttask.util.getOrAwaitValue

@OptIn(ExperimentalCoroutinesApi::class)
class GetShipmentGroupByHighlightUseCaseTest {
    private lateinit var dao: ShipmentNetworkDao
    private lateinit var api: ShipmentApi

    private lateinit var repository: ShipmentNetworkRepository
    private lateinit var getShipmentGroupByHighlightUseCase: GetShipmentGroupByHighlightUseCase

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() = runTest {
        dao = FakeShipmentNetworkDaoImpl()
        api = FakeShipmentApi()
        repository = FakeRepositoryImpl(dao, api)
        getShipmentGroupByHighlightUseCase = GetShipmentGroupByHighlightUseCase(repository)

    }
    @Test
    fun testGetGroupedShipmentsByHighlight() = runTest {
        val highlightedEntity = mockShipmentNetwork().toEntity().apply {
            this.operations.highlight = true
        }
        val nonHighlightedEntity = mockShipmentNetwork().toEntity()
        dao.saveShipments(listOf(highlightedEntity, nonHighlightedEntity))
        val pair = getShipmentGroupByHighlightUseCase.getGroupedShipmentsByHighlight().getOrAwaitValue()
        val highlighted = pair.first[0]
        val nonHighlighted = pair.second[0]
        assertTrue(highlighted.operations.highlight)
        assertTrue(!nonHighlighted.operations.highlight)
    }

    @Test
    fun testSyncShipments() = runTest {
        repeat(2) {
            getShipmentGroupByHighlightUseCase.syncShipments()
        }
        val pair = getShipmentGroupByHighlightUseCase.getGroupedShipmentsByHighlight().getOrAwaitValue()
        assertTrue(pair.second.isNotEmpty())
        assertEquals(pair.second.size, 2)
    }
    @Test
    fun testSyncShipmentsReturnsEmptyListOnFirstCall() = runTest {
        repeat(1) {
            getShipmentGroupByHighlightUseCase.syncShipments()
        }
        val pair = getShipmentGroupByHighlightUseCase.getGroupedShipmentsByHighlight().getOrAwaitValue()
        assertTrue(pair.second.isEmpty())
        assertEquals(pair.second.size, 0)
    }
}