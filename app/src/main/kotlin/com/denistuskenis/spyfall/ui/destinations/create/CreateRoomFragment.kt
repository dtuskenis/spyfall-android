package com.denistuskenis.spyfall.ui.destinations.create

import android.os.Bundle
import android.view.View
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.databinding.FragmentCreateBinding as ViewBinding

class CreateRoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButton.setOnClickListener {
            navController.navigate(CreateRoomFragmentDirections.toWaitingRoom())
        }
    }
}
