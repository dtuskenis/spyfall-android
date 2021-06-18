package com.denistuskenis.spyfall.ui.destinations.home

import android.os.Bundle
import android.view.View
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.databinding.FragmentHomeBinding as ViewBinding

class HomeFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            createRoomButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toCreateRoom())
            }
            joinRoomButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toJoinRoom())
            }
            locationsReferenceButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toLocationsReference())
            }
        }
    }
}
