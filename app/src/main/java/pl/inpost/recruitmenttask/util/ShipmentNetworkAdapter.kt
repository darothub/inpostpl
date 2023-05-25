package pl.inpost.recruitmenttask.util

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.viewbinding.BindableItem
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.data.model.ShipmentStatus
import pl.inpost.recruitmenttask.databinding.ShipmentHeaderItemBinding
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.Date
@RequiresApi(Build.VERSION_CODES.O)
class ShipmentNetworkAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: MutableList<ListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ListItem.TYPE_HEADER) {
            val binding = ShipmentHeaderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ShipmentNetworkHeaderViewHolder(binding)
        }
        val binding = ShipmentItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ShipmentNetworkBodyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if (item.getType() == ListItem.TYPE_BODY) {
            holder as ShipmentNetworkBodyViewHolder
            item as BodyItem<ShipmentNetworkEntity>
            holder.bind(item.data)
        } else {
            holder as ShipmentNetworkHeaderViewHolder
            item as HeaderItem
            holder.bind(item.title)
        }
    }

    fun setData(listItem: List<ListItem>) {
        list.addAll(listItem)
        notifyDataSetChanged()
    }
    override fun getItemViewType(position: Int): Int {
        return list[position].getType()
    }
}
@RequiresApi(Build.VERSION_CODES.O)
class ShipmentNetworkBodyViewHolder(private val binding: ShipmentItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(shipment: ShipmentNetworkEntity) {
        val context = binding.root.context
        val date = shipment.pickUpDate ?: shipment.expiryDate ?: shipment.storedDate
        val dateStr = date?.toInPostDateString()
        binding.numberOfParcelValueTv.text = shipment.number
        binding.statusValueTv.text = shipment.status.name
        binding.senderValueTv.text = shipment.sender?.email
        binding.waitingTv.text = context.getString(shipment.status.nameRes)
        binding.waitingValueTv.text = dateStr
    }
}

class ShipmentNetworkHeaderViewHolder(private val binding: ShipmentHeaderItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(header: String) {
        binding.headerTv.text = header
    }
}

class ShipmentBodyLayout(private val shipment: ShipmentNetworkEntity): BindableItem<ShipmentItemBinding>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(viewBinding: ShipmentItemBinding, position: Int) {
        val context = viewBinding.root.context
        val date = shipment.pickUpDate ?: shipment.expiryDate ?: shipment.storedDate
        val dateStr = date?.toInPostDateString()
        viewBinding.numberOfParcelValueTv.text = shipment.number
        viewBinding.statusValueTv.text = shipment.status.name
        viewBinding.senderValueTv.text = shipment.sender?.name
        viewBinding.waitingTv.text = context.getString(shipment.status.nameRes)
        viewBinding.waitingValueTv.text = dateStr
    }

    override fun getLayout() = R.layout.shipment_item

    override fun initializeViewBinding(view: View): ShipmentItemBinding = ShipmentItemBinding.bind(view)
}

class ShipmentHeaderLayout(private val header: String): BindableItem<ShipmentHeaderItemBinding>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(viewBinding: ShipmentHeaderItemBinding, position: Int) {
        viewBinding.headerTv.text = header
    }

    override fun getLayout() = R.layout.shipment_header_item

    override fun initializeViewBinding(view: View): ShipmentHeaderItemBinding = ShipmentHeaderItemBinding.bind(view)
}