package com.denistuskenis.spyfall.ui.destinations.waiting

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.denistuskenis.spyfall.R
import kotlinx.coroutines.delay

class WaitingRoomFragment: Fragment(R.layout.fragment_waiting) {

    private val navController: NavController by lazy { findNavController() }

    private var isWaitingOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.coroutineScope.launchWhenStarted {
            delay(STUB_WAITING_TIME_MILLISECONDS)

            isWaitingOver = true

            navController.navigate(WaitingRoomFragmentDirections.toRoom())
        }
    }

    override fun onStart() {
        super.onStart()

        if (isWaitingOver) {
            navController.popBackStack()
        }
    }

    private companion object {
        const val STUB_WAITING_TIME_MILLISECONDS = 3000L
    }
}
