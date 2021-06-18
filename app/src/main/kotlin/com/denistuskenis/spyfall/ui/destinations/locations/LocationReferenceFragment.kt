package com.denistuskenis.spyfall.ui.destinations.locations

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.databinding.ItemLocationBinding
import com.denistuskenis.spyfall.model.GameLocation
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.adapter.ReusableListAdapter
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import kotlinx.coroutines.launch
import com.denistuskenis.spyfall.databinding.FragmentLocationsBinding as ViewBinding

class LocationReferenceFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    private val locationsAdapter = ReusableListAdapter(
        bindData = { location: GameLocation, binding ->
            with(binding) {
                locationNameView.text = location.name
                locationImageView.load(location.imageUrl)
            }
        },
        inflateBinding = ItemLocationBinding::inflate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            locationsView.adapter = locationsAdapter
            toggleGridButton.setOnClickListener { toggleLocationsViewSpanCount() }
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            RoomsManager.locations().also(locationsAdapter::submitList)
        }
    }

    private fun ViewBinding.toggleLocationsViewSpanCount() {
        (locationsView.layoutManager as? GridLayoutManager)?.let { grid ->
            if (grid.spanCount == 1) {
                grid.spanCount = 2
                toggleGridButton.setImageResource(R.drawable.ic_grid_off_24dp)
            } else {
                grid.spanCount = 1
                toggleGridButton.setImageResource(R.drawable.ic_grid_on_24dp)
            }
        }
    }
}
