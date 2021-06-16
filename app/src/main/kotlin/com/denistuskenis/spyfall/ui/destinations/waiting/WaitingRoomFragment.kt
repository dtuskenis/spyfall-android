package com.denistuskenis.spyfall.ui.destinations.waiting

import android.os.Bundle
import android.view.View
import androidx.lifecycle.coroutineScope
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.model.RoomState
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.ui.destinations.room.CivilRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.denistuskenis.spyfall.databinding.FragmentWaitingBinding as ViewBinding

class WaitingRoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    private var isWaitingOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.coroutineScope.launchWhenStarted {
            while (true) {
                if (isWaitingOver) {
                    navController.popBackStack()
                } else {
                    RoomsManager.check()?.let { roomState ->
                        when (roomState) {
                            is RoomState.Waiting -> {
                                binding.playerReadinessView.text = getString(
                                    R.string.waiting_room_screen_players_readiness_format,
                                    roomState.numberOfReadyPlayers,
                                    roomState.numberOfPlayers
                                )
                            }
                            is RoomState.GameStarted -> {
                                isWaitingOver = true
                                navController.navigate(WaitingRoomFragmentDirections.toRoom(
                                    civilRole = (roomState as? RoomState.GameStarted.AsCivil)?.let {
                                        CivilRole(
                                            role = it.role,
                                            locationName = it.locationName,
                                        )
                                    }
                                ))
                            }
                        }
                    } ?: navController.popBackStack()
                }
                delay(GAME_STATE_POLLING_INTERVAL_MILLISECONDS)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            readyButton.setOnClickListener {
                readyButton.isEnabled = false
                lifecycle.coroutineScope.launch {
                    RoomsManager.ready()
                }
            }
        }
    }

    private companion object {
        const val GAME_STATE_POLLING_INTERVAL_MILLISECONDS = 500L
    }
}