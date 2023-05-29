package pl.inpost.recruitmenttask.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.recruitmenttask.data.FakeRepositoryImpl
import pl.inpost.recruitmenttask.data.FakeShipmentApi
import pl.inpost.recruitmenttask.data.FakeShipmentNetworkDaoImpl
import pl.inpost.recruitmenttask.data.MainCoroutineRule
import pl.inpost.recruitmenttask.data.local.dao.ShipmentNetworkDao
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.repository.ShipmentNetworkRepository
import pl.inpost.recruitmenttask.domain.usecases.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentGroupByHighlightUseCase
import pl.inpost.recruitmenttask.presentation.viewmodel.ShipmentListViewModel
import pl.inpost.recruitmenttask.util.getOrAwaitValue
@OptIn(ExperimentalCoroutinesApi::class)
class ShipmentNetworkViewModelTest {
    private lateinit var dao: ShipmentNetworkDao
    private lateinit var api: ShipmentApi

    private lateinit var repository: ShipmentNetworkRepository
    private lateinit var getShipmentGroupByHighlightUseCase: GetShipmentGroupByHighlightUseCase
    private  lateinit var archiveShipmentUseCase: ArchiveShipmentUseCase

    private  lateinit var viewModel: ShipmentListViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()


    @Before
    fun setUp() = runTest {
        dao = FakeShipmentNetworkDaoImpl()
        api = FakeShipmentApi()
        repository = FakeRepositoryImpl(dao, api)
        getShipmentGroupByHighlightUseCase = GetShipmentGroupByHighlightUseCase(repository)
        archiveShipmentUseCase = ArchiveShipmentUseCase(repository)
        viewModel = ShipmentListViewModel(getShipmentGroupByHighlightUseCase, archiveShipmentUseCase)

    }

    @Test
    fun refreshData() = runTest  {
        viewModel.refreshData()
        advanceUntilIdle()
        val data = viewModel.groupedShipmentsByHighlight.getOrAwaitValue()
        assert(data.second.size == 2)
    }
    @Test
    fun testArchive() = runTest {
        viewModel.refreshData()
        advanceUntilIdle()
        val data = viewModel.groupedShipmentsByHighlight.getOrAwaitValue()
        viewModel.archive(data.second[0])
        advanceUntilIdle()
        val archived = viewModel.groupedShipmentsByHighlight.getOrAwaitValue().second[0]
        println(archived.archived)
    }
}