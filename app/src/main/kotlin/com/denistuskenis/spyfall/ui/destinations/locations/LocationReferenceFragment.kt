package com.denistuskenis.spyfall.ui.destinations.locations

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.clear
import coil.load
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.databinding.ItemLocationBinding
import com.denistuskenis.spyfall.model.GameLocation
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.adapter.ListItem
import com.denistuskenis.spyfall.ui.adapter.ReusableListAdapter
import com.denistuskenis.spyfall.ui.adapter.asListItems
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.ui.errors.handleWithDefaultErrorHandler
import kotlinx.coroutines.launch
import com.denistuskenis.spyfall.databinding.FragmentLocationsBinding as ViewBinding

class LocationReferenceFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    private val locationsAdapter = ReusableListAdapter(
        bindData = { (_, locationItem): IndexedValue<ListItem<GameLocation>>, binding ->
            with(binding) {
                when (locationItem) {
                    is ListItem.Placeholder -> {
                        locationNameView.text = null
                        locationImageView.clear()
                    }
                    is ListItem.Data -> {
                        locationNameView.text = locationItem.location.name
                        locationImageView.load(locationItem.location.imageUrl) {
                            crossfade(true)
                        }
                    }
                }
            }
        },
        inflateBinding = ItemLocationBinding::inflate,
        areItemsTheSame = { a, b -> a.index == b.index },
        areContentsTheSame = Any::equals
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

        val placeholderItems = generateSequence {
            ListItem.Placeholder
        }.take(NUMBER_OF_PLACEHOLDER_ITEMS).withIndex().toList()

        locationsAdapter.submitList(placeholderItems)

        lifecycleScope.launch {
            handleWithDefaultErrorHandler(
                result = RoomsManager.locations(),
                onSuccess = {
                    locationsAdapter.submitList(it.asListItems().withIndex().toList())
                },
            )
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

    private companion object {
        const val NUMBER_OF_PLACEHOLDER_ITEMS = 20
    }
}
