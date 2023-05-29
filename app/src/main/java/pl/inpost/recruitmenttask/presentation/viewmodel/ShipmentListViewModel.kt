package pl.inpost.recruitmenttask.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.domain.usecases.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentGroupByHighlightUseCase
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getShipmentGroupByHighlightUseCase: GetShipmentGroupByHighlightUseCase,
    private val archiveShipmentUseCase: ArchiveShipmentUseCase
) : ViewModel() {

    val groupedShipmentsByHighlight: LiveData<Pair<List<ShipmentNetworkEntity>, List<ShipmentNetworkEntity>>> by lazy {
        getShipmentGroupByHighlightUseCase.getGroupedShipmentsByHighlight()
    }
    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            getShipmentGroupByHighlightUseCase.syncShipments()
        }
    }

    fun archive(entity: ShipmentNetworkEntity) {
        viewModelScope.launch {
            archiveShipmentUseCase.archive(entity)
        }
    }

}
