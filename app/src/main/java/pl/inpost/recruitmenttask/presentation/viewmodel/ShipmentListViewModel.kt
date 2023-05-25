package pl.inpost.recruitmenttask.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.model.ShipmentNetwork
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentGroupByHighlightUseCase
import pl.inpost.recruitmenttask.util.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getShipmentGroupByHighlightUseCase: GetShipmentGroupByHighlightUseCase
) : ViewModel() {

    private val mutableViewState = MutableLiveData<Map<Boolean, List<ShipmentNetworkEntity>>>(
        hashMapOf()
    )
    val viewState: LiveData<Map<Boolean, List<ShipmentNetworkEntity>>> = mutableViewState

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            val shipments = getShipmentGroupByHighlightUseCase.groupingByHighlight()
            mutableViewState.setState { shipments }
        }
    }
}
