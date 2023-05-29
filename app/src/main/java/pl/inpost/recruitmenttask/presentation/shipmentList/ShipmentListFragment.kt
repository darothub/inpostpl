package pl.inpost.recruitmenttask.presentation.shipmentList

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemLongClickListener
import com.xwray.groupie.Section

import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding

import pl.inpost.recruitmenttask.presentation.adapter.ShipmentBodyLayoutBinder
import pl.inpost.recruitmenttask.presentation.adapter.ShipmentHeaderLayoutBinder
import pl.inpost.recruitmenttask.presentation.viewmodel.ShipmentListViewModel
import pl.inpost.recruitmenttask.util.viewBinding


@AndroidEntryPoint
class ShipmentListFragment : Fragment(R.layout.fragment_shipment_list) {
    private val binding by viewBinding(FragmentShipmentListBinding::bind)
    private val viewModel: ShipmentListViewModel by viewModels()
    private val groupAdapter = GroupieAdapter()
    private val highlightSection = Section()
    private val nonHighlightSection = Section()
    private val highlightBodyItemList = mutableListOf<ShipmentBodyLayoutBinder>()
    private val nonHighlightBodyItemList = mutableListOf<ShipmentBodyLayoutBinder>()
    private val onItemLongClickListener = OnItemLongClickListener { item, _ ->
        item as ShipmentBodyLayoutBinder
        showPopMenuFor(item)
        true
    }
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

        handleShipmentWithGrouping(groupAdapter)
        groupAdapter.setOnItemLongClickListener(onItemLongClickListener)
        highlightSection.setHeader(ShipmentHeaderLayoutBinder("Highlighted"))
        nonHighlightSection.setHeader(ShipmentHeaderLayoutBinder("Non Highlighted"))
        highlightSection.setHideWhenEmpty(true)
        nonHighlightSection.setHideWhenEmpty(true)
        groupAdapter.add(highlightSection)
        groupAdapter.add(nonHighlightSection)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeRefresh.isRefreshing = false
        }
    }


    private fun handleShipmentWithGrouping(groupAdapter: GroupieAdapter) {
        viewModel.groupedShipmentsByHighlight.observe(requireActivity()) { shipmentsPair ->
            highlightBodyItemList.clear()
            nonHighlightBodyItemList.clear()
            shipmentsPair.first.forEach {
                val item = ShipmentBodyLayoutBinder(it)
                highlightBodyItemList.add(item)
            }
            shipmentsPair.second.forEach {
                val item = ShipmentBodyLayoutBinder(it)
                nonHighlightBodyItemList.add(item)
            }

            highlightSection.update(highlightBodyItemList)
            nonHighlightSection.update(nonHighlightBodyItemList)
        }

        binding.recyclerView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun showPopMenuFor(shipmentBodyLayout: ShipmentBodyLayoutBinder) {
        val popupMenu = PopupMenu(requireContext(), binding.root)
        popupMenu.menuInflater.inflate(R.menu.list_item_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_archive -> {
                    viewModel.archive(shipmentBodyLayout.shipment)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


}
