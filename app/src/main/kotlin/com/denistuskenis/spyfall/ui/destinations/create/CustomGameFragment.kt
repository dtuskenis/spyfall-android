package com.denistuskenis.spyfall.ui.destinations.create

import android.os.Bundle
import android.view.View
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.databinding.ItemRoomBinding
import com.denistuskenis.spyfall.model.Room
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.adapter.ReusableListAdapter
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.ui.operations.performBlockingOperationWithDefaultErrorHandler
import com.denistuskenis.spyfall.databinding.FragmentCustomGameBinding as ViewBinding

class CustomGameFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    private val roomsAdapter = ReusableListAdapter(
        bindData = { room: Room, binding ->
            binding.roomNameView.text = room.name
            binding.root.setOnClickListener {
                performBlockingOperationWithDefaultErrorHandler(
                    getResult = { RoomsManager.join(room.id) },
                    onSuccess = {
                        navController.navigate(CustomGameFragmentDirections.toWaitingRoom())
                    },
                )
            }
        },
        inflateBinding = ItemRoomBinding::inflate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            createButton.setOnClickListener {
                validateRoomName { roomName ->
                    performBlockingOperationWithDefaultErrorHandler(
                        getResult = { RoomsManager.create(roomName = roomName) },
                        onSuccess = {
                            navController.navigate(CustomGameFragmentDirections.toWaitingRoom())
                        },
                    )
                }
            }
            refreshButton.setOnClickListener { refresh() }
            roomsView.adapter = roomsAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        refresh()
    }

    private fun validateRoomName(onSuccess: (roomName: String) -> Unit) {
        val roomNameView = binding.roomNameView
        val roomName = roomNameView.text?.toString().orEmpty()
        when {
            roomName.isEmpty() || roomName.isBlank() ->
                roomNameView.error = getString(R.string.custom_game_screen_empty_room_name_error)
            else -> onSuccess(roomName)
        }
    }

    private fun refresh() {
        performBlockingOperationWithDefaultErrorHandler(
            getResult = RoomsManager::search,
            onSuccess = roomsAdapter::submitList,
        )
    }
}
