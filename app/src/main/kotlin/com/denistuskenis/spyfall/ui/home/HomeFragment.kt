package com.denistuskenis.spyfall.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        val navController = findNavController()

        with(binding) {
            createRoomButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toCreateRoom())
            }
            joinRoomButton.setOnClickListener {
                navController.navigate(HomeFragmentDirections.toJoinRoom())
            }
        }
    }
}
