package com.denistuskenis.spyfall.ui.destinations.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.ui.operations.performBlockingOperationWithDefaultErrorHandler
import com.denistuskenis.spyfall.databinding.FragmentHomeBinding as ViewBinding

class HomeFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            findGameButton.setOnClickListener {
                performBlockingOperationWithDefaultErrorHandler(
                    getResult = RoomsManager::find,
                    onSuccess = {
                        navController.navigate(HomeFragmentDirections.toWaitingRoom())
                    },
                )
            }
            customGameButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toCustomGame())
            }
            locationsReferenceButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toLocationsReference())
            }
            gameRulesButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toGameRules())
            }
            shareButton.setOnClickListener {
                with(Intent()) {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.app_sharing_link))
                    type = "text/plain"
                    startActivity(Intent.createChooser(this, ""))
                }
            }
        }
    }
}
