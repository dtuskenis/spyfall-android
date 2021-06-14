package com.denistuskenis.spyfall.ui.destinations.waiting

import android.os.Bundle
import androidx.lifecycle.coroutineScope
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import kotlinx.coroutines.delay
import com.denistuskenis.spyfall.databinding.FragmentWaitingBinding as ViewBinding

class WaitingRoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

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
