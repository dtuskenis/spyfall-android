package com.denistuskenis.spyfall.ui.destinations.create

import android.os.Bundle
import android.view.View
import androidx.lifecycle.coroutineScope
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import kotlinx.coroutines.launch
import com.denistuskenis.spyfall.databinding.FragmentCreateBinding as ViewBinding

class CreateRoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            createButton.setOnClickListener {
                lifecycle.coroutineScope.launch {
                    RoomsManager.create(roomName = roomNameView.text.toString())
                    navController.navigate(CreateRoomFragmentDirections.toWaitingRoom())
                }
            }
        }
    }
}
