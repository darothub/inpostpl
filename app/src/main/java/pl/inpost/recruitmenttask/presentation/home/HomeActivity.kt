package pl.inpost.recruitmenttask.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.ActivityHomeBinding
import pl.inpost.recruitmenttask.presentation.shipmentList.ShipmentListFragment
import pl.inpost.recruitmenttask.util.viewBinding

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityHomeBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
