package pl.inpost.recruitmenttask.presentation.adapter

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.xwray.groupie.viewbinding.BindableItem
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.ShipmentHeaderItemBinding


class ShipmentHeaderAdapter(private val header: String): BindableItem<ShipmentHeaderItemBinding>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(viewBinding: ShipmentHeaderItemBinding, position: Int) {
        viewBinding.headerTv.text = header
    }

    override fun getLayout() = R.layout.shipment_header_item

    override fun initializeViewBinding(view: View): ShipmentHeaderItemBinding = ShipmentHeaderItemBinding.bind(view)
}