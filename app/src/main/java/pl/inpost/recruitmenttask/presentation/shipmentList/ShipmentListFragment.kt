package pl.inpost.recruitmenttask.presentation.shipmentList

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.presentation.adapter.HeaderItem
import pl.inpost.recruitmenttask.presentation.adapter.ListItem
import pl.inpost.recruitmenttask.presentation.adapter.ShipmentBodyLayout
import pl.inpost.recruitmenttask.presentation.adapter.ShipmentHeaderLayout
import pl.inpost.recruitmenttask.presentation.adapter.ShipmentNetworkAdapter
import pl.inpost.recruitmenttask.presentation.viewmodel.ShipmentListViewModel
import pl.inpost.recruitmenttask.util.viewBinding


@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class ShipmentListFragment : Fragment(R.layout.fragment_shipment_list) {
    private val binding by viewBinding(FragmentShipmentListBinding::bind)
    private val viewModel: ShipmentListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shipment_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groupAdapter = GroupieAdapter()
        viewModel.viewState.observe(requireActivity()) { shipments ->
            shipments.forEach { shipmentNetwork ->
                if(shipmentNetwork.key) {
                    handleGroupieSectioning("Highlighted", shipmentNetwork, groupAdapter)
                } else {
                    handleGroupieSectioning("Not Highlighted", shipmentNetwork, groupAdapter)
                }
            }
            binding.recyclerView.apply {
                adapter = groupAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            Log.d("ShipmentFragment", "Refresh")
        }
    }

    private fun handleGroupieSectioning(
        header: String,
        shipmentNetwork: Map.Entry<Boolean, List<ShipmentNetworkEntity>>,
        groupAdapter: GroupieAdapter
    ) {
        val section = Section()
        section.setHeader(ShipmentHeaderLayout("Highlighted"))
        shipmentNetwork.value.forEach {
            val item = ShipmentBodyLayout(it)
            section.add(item)
        }
        groupAdapter.add(section)
    }
}
