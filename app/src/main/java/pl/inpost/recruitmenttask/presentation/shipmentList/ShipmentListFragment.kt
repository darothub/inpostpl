package pl.inpost.recruitmenttask.presentation.shipmentList

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.presentation.adapter.OnLongPressListener
import pl.inpost.recruitmenttask.presentation.adapter.ShipmentBodyAdapter
import pl.inpost.recruitmenttask.presentation.adapter.ShipmentHeaderAdapter
import pl.inpost.recruitmenttask.presentation.viewmodel.ShipmentListViewModel
import pl.inpost.recruitmenttask.util.viewBinding


@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class ShipmentListFragment : Fragment(R.layout.fragment_shipment_list), OnLongPressListener {
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
        handleShipmentWithGrouping(groupAdapter)

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun handleShipmentWithGrouping(groupAdapter: GroupieAdapter) {
        viewModel.viewState.observe(requireActivity()) { shipments ->
            shipments.forEach { shipmentNetwork ->
                if (shipmentNetwork.key) {
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
    }

    private fun handleGroupieSectioning(
        header: String,
        shipmentNetwork: Map.Entry<Boolean, List<ShipmentNetworkEntity>>,
        groupAdapter: GroupieAdapter
    ) {
        val section = Section()
        section.setHeader(ShipmentHeaderAdapter(header))
        shipmentNetwork.value.forEach {
            val item = ShipmentBodyAdapter(it, this)
            section.add(item)
        }
        groupAdapter.add(section)
    }

    override fun onBodyLongPress(entity: ShipmentNetworkEntity) {
        showPopMenuFor(entity)
    }

    private fun showPopMenuFor(entity: ShipmentNetworkEntity) {
        // Create and show a context menu (popup menu) with menu items
        val popupMenu = PopupMenu(requireContext(), binding.root)
        popupMenu.menuInflater.inflate(R.menu.list_item_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_archive -> {
                    Log.d("ShipmentFrag", "Archived $entity")
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}
