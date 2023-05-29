package pl.inpost.recruitmenttask.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.model.toEntity
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.network.api.mockShipmentNetwork
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import pl.inpost.recruitmenttask.domain.usecases.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentGroupByHighlightUseCase
import pl.inpost.recruitmenttask.util.getOrAwaitValue

@OptIn(ExperimentalCoroutinesApi::class)
class ArchiveShipmentUseCaseTest {

    private lateinit var dao: ShipmentNetworkDao
    private lateinit var api: ShipmentApi

    private lateinit var repository: ShipmentNetworkRepository
    private lateinit var archiveShipmentUseCase: ArchiveShipmentUseCase

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
        archiveShipmentUseCase = ArchiveShipmentUseCase(repository)
        repeat(2) {
            repository.getApiShipments()
        }
    }
    @Test
    fun testArchive() = runTest {
        val entity = repository.getShipments().getOrAwaitValue()[0]
        archiveShipmentUseCase.archive(entity)
        val archivedEntity = repository.getShipments().getOrAwaitValue()[0]
        println(archivedEntity)
        assertTrue(archivedEntity.archived)
    }
}