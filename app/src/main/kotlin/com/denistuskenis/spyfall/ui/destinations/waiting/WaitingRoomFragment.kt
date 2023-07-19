package com.denistuskenis.spyfall.ui.destinations.waiting

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.model.RoomState
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.ui.destinations.room.CivilRole
import com.denistuskenis.spyfall.ui.errors.handleWithDefaultErrorHandler
import kotlinx.coroutines.launch
import com.denistuskenis.spyfall.databinding.FragmentWaitingBinding as ViewBinding

class WaitingRoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            RoomsManager.check().collect {
                handleWithDefaultErrorHandler(
                    result = it,
                    onSuccess = ::handleRoomState,
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            readyButton.setOnClickListener {
                readyButton.isEnabled = false
                lifecycleScope.launch {
                    handleWithDefaultErrorHandler(
                        result = RoomsManager.ready(),
                        onError = { readyButton.isEnabled = true }
                    )
                }
            }
        }
    }

    private fun handleRoomState(roomState: RoomState?) {
        when (roomState) {
            is RoomState.Waiting -> {
                binding.playerReadinessView.text = getString(
                    R.string.waiting_room_screen_players_readiness_format,
                    roomState.numberOfReadyPlayers,
                    roomState.numberOfPlayers
                )
            }
            is RoomState.GameStarted -> {
                navController.navigate(
                    WaitingRoomFragmentDirections.toRoom(
                        civilRole = (roomState as? RoomState.GameStarted.AsCivil)?.let {
                            CivilRole(
                                role = it.role,
                                locationName = it.locationName,
                            )
                        },
                        cardBackImageUrl = roomState.cardBackImageUrl,
                        cardFrontImageUrl = when (roomState) {
                            is RoomState.GameStarted.AsSpy -> roomState.cardFrontImageUrl
                            is RoomState.GameStarted.AsCivil -> roomState.locationImageUrl
                        }
                    )
                )
            }
            null -> navController.popBackStack()
        }
    }
}
