package com.denistuskenis.spyfall.ui.destinations.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.databinding.FragmentCreateBinding

class CreateRoomFragment: Fragment(R.layout.fragment_create) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCreateBinding.bind(view)
        val navController = findNavController()

        binding.createButton.setOnClickListener {
            navController.navigate(CreateRoomFragmentDirections.toWaitingRoom())
        }
    }
}
