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
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.presentation.viewmodel.ShipmentListViewModel
import pl.inpost.recruitmenttask.util.BodyItem
import pl.inpost.recruitmenttask.util.HeaderItem
import pl.inpost.recruitmenttask.util.ListItem
import pl.inpost.recruitmenttask.util.ShipmentBodyLayout
import pl.inpost.recruitmenttask.util.ShipmentHeaderLayout
import pl.inpost.recruitmenttask.util.ShipmentNetworkAdapter

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class ShipmentListFragment : Fragment() {

    private val viewModel: ShipmentListViewModel by viewModels()
    private var binding: FragmentShipmentListBinding? = null
    lateinit var listAdapter: ShipmentNetworkAdapter
    lateinit var listOfItems: MutableList<ListItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shipment_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentShipmentListBinding.inflate(inflater, container, false)
        return requireNotNull(binding).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ShipmentNetworkAdapter()
        listOfItems = mutableListOf()
        val groupAdapter = GroupieAdapter()
        viewModel.viewState.observe(requireActivity()) { shipments ->
            shipments.forEach { shipmentNetwork ->
                if(shipmentNetwork.key) {
                    val header = HeaderItem("Highlighted")
                    listOfItems.add(header)
                    val section = Section()
                    section.setHeader(ShipmentHeaderLayout("Highlighted"))
                    shipmentNetwork.value.forEach {
                        val item = ShipmentBodyLayout(it)
                        section.add(item)
                    }
                    groupAdapter.add(section)
                } else {
                    val section = Section()
                    section.setHeader(ShipmentHeaderLayout("Not Highlighted"))
                    shipmentNetwork.value.forEach {
                        val item = ShipmentBodyLayout(it)
                        section.add(item)
                    }
                    groupAdapter.add(section)
                }
            }
            binding?.recyclerView?.adapter = groupAdapter
            binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        }

        binding?.swipeRefresh?.setOnRefreshListener {
            Log.d("ShipmentFragment", "Refresh")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = ShipmentListFragment()
    }
}

//shipments.forEach { shipmentNetwork ->
//    if(shipmentNetwork.key) {
//        val header = HeaderItem("Highlighted")
//        Log.d("ListFragHighlight", "adding header...")
//        listOfItems.add(header)
//        shipmentNetwork.value.forEach {
//            val body = BodyItem(it)
//            Log.d("ListFragHighlight", "adding body...")
//            listOfItems.add(body)
//        }
//        Log.d("ListFragHighlight", "finished adding header and body...")
//
//    } else {
//        val header = HeaderItem("Not Highlighted")
//        Log.d("ListFragNotHighlight", "adding header...")
//        listOfItems.add(header)
//        shipmentNetwork.value.forEach {
//            val body = BodyItem(it)
//            Log.d("ListFragNotHighlight", "adding body...")
//            listOfItems.add(body)
//        }
//        Log.d("ListFragNotHighlight", "finished adding header and body...")
//    }
//    Log.d("ListFragment", "Set data")
//    listAdapter.setData(listOfItems)
//}
//Log.d("ListFragment", "Setting adapter")
//binding?.recyclerView?.adapter = listAdapter
//binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())