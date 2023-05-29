package pl.inpost.recruitmenttask.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.model.toEntity
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.network.api.mockShipmentNetwork
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepositoryImpl
import pl.inpost.recruitmenttask.util.getOrAwaitValue
import pl.inpost.recruitmenttask.util.setState


@ExperimentalCoroutinesApi
class RepositoryTest {

    private lateinit var dao: ShipmentNetworkDao
    private lateinit var api: ShipmentApi
    private lateinit var repository: ShipmentNetworkRepository
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()
    @Before
    fun setUp() {
        dao = FakeShipmentNetworkDaoImpl()
        api = FakeShipmentApi()
        repository = FakeRepositoryImpl(dao, api)
    }

    @Test
    fun testGetShipment() = runTest {
        val data = repository.getShipments().getOrAwaitValue()
        assert(data.isEmpty())
    }
    @Test
    fun getApiShipments() = runTest{
        repeat(2) {
            repository.getApiShipments()
        }
        val data: List<ShipmentNetworkEntity> = repository.getShipments().getOrAwaitValue()
        assert(data.isNotEmpty())
    }
    @Test
    fun update() = runTest {
        repeat(2) {
            repository.getApiShipments()
        }
        val data: List<ShipmentNetworkEntity> = repository.getShipments().getOrAwaitValue()
        val mockShipment = data[0]
        mockShipment.archived = true
        repository.update(mockShipment)

        val archiveEntity = data.find { it.archived }
        archiveEntity?.archived?.let { assertTrue(it) }
    }
}

@RunWith(MockitoJUnitRunner::class)
class RepositoryMockTest {
    @MockK
    lateinit var dao: ShipmentNetworkDao
    @MockK
    lateinit var api: ShipmentApi

    lateinit var repository: ShipmentNetworkRepository
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = ShipmentNetworkRepositoryImpl(api, dao)
    }
    @Test
    fun testGetShipment()  {
        val liveData = MutableLiveData(emptyList<ShipmentNetworkEntity>())
        every { dao.getShipmentByOrder() } returns liveData
        val data = repository.getShipments().getOrAwaitValue()
        verify { dao.getShipmentByOrder() }
        assert(data.isEmpty())
    }
    @Test
    fun getApiShipments() = runTest {
        val liveData = MutableLiveData(emptyList<ShipmentNetworkEntity>())
        coEvery { dao.getCount() } returns 0
        coEvery { api.getShipments() } returns emptyList() andThen listOf(mockShipmentNetwork(), mockShipmentNetwork())
        repeat(2) {
            repository.getApiShipments()
        }
        liveData.setState { listOf(mockShipmentNetwork().toEntity(), mockShipmentNetwork().toEntity()) }
        coEvery { dao.getShipmentByOrder() } returns  liveData
        coVerify(atLeast = 2) {
            api.getShipments()
        }
        assert(repository.getShipments().getOrAwaitValue().size == 2)
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher() {

    val testDispatcher = StandardTestDispatcher()

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}