package com.denistuskenis.spyfall.ui.destinations.create

import android.os.Bundle
import android.view.View
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.ui.OnSeekBarProgressChangeListener
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.databinding.FragmentCreateBinding as ViewBinding

class CreateRoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            createButton.setOnClickListener {
                navController.navigate(CreateRoomFragmentDirections.toWaitingRoom())
            }

            numberOfPlayersView.max = MAX_NUMBER_OF_PLAYERS - MIN_NUMBER_OF_PLAYERS
            numberOfPlayersView.setOnSeekBarChangeListener(OnSeekBarProgressChangeListener {
                updateNumberOfPlayersView()
            })
            updateNumberOfPlayersView()
        }
    }

    private fun ViewBinding.updateNumberOfPlayersView() {
        val numberOfPlayers =
            numberOfPlayersView.progress + MIN_NUMBER_OF_PLAYERS

        numberOfPlayersTitle.text = getString(
            R.string.create_room_screen_number_of_players_format,
            numberOfPlayers
        )
    }

    private companion object {
        const val MIN_NUMBER_OF_PLAYERS = 3
        const val MAX_NUMBER_OF_PLAYERS = 8
    }
}
