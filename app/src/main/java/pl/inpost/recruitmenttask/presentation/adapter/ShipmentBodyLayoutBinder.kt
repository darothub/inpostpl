package pl.inpost.recruitmenttask.presentation.adapter

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.xwray.groupie.viewbinding.BindableItem
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.data.local.entities.ShipmentNetworkEntity
import pl.inpost.recruitmenttask.databinding.ShipmentHeaderItemBinding
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.util.toInPostDateString

class ShipmentBodyLayoutBinder(val shipment: ShipmentNetworkEntity): BindableItem<ShipmentItemBinding>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(viewBinding: ShipmentItemBinding, position: Int) {
        val sender = if (shipment.sender?.email?.isEmpty() == true) {
            shipment.sender.name
        } else {
            shipment.sender?.email
        }
        val context = viewBinding.root.context
        val date = shipment.pickUpDate ?: shipment.expiryDate ?: shipment.storedDate
        val dateStr = date?.toInPostDateString()
        viewBinding.numberOfParcelValueTv.text = shipment.number
        viewBinding.statusValueTv.text = context.getString(shipment.status.nameRes)
        viewBinding.senderValueTv.text = sender
        viewBinding.waitingTv.text = context.getString(shipment.status.nameRes)
        viewBinding.waitingValueTv.text = dateStr
    }

    override fun getLayout() = R.layout.shipment_item

    override fun initializeViewBinding(view: View): ShipmentItemBinding = ShipmentItemBinding.bind(view)
}

