package com.denistuskenis.spyfall.ui.destinations.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.ui.errors.handleWithDefaultErrorHandler
import com.denistuskenis.spyfall.ui.progress.hideBlockingProgress
import com.denistuskenis.spyfall.ui.progress.showBlockingProgress
import kotlinx.coroutines.launch
import com.denistuskenis.spyfall.databinding.FragmentHomeBinding as ViewBinding

class HomeFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            findRoomButton.setOnClickListener {
                showBlockingProgress()
                lifecycleScope.launch {
                    handleWithDefaultErrorHandler(
                        result = RoomsManager.find(),
                        onSuccess = {
                            navController.navigate(HomeFragmentDirections.toWaitingRoom())
                        },
                    )
                }.invokeOnCompletion { hideBlockingProgress() }
            }
            createRoomButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toCreateRoom())
            }
            joinRoomButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toJoinRoom())
            }
            locationsReferenceButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toLocationsReference())
            }
            gameRulesButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toGameRules())
            }
        }
    }
}
