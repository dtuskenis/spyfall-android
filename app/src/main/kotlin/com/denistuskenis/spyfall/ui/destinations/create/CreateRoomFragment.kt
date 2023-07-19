package com.denistuskenis.spyfall.ui.destinations.create

import android.os.Bundle
import android.view.View
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.ui.operations.performBlockingOperationWithDefaultErrorHandler
import com.denistuskenis.spyfall.databinding.FragmentCreateBinding as ViewBinding

class CreateRoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            createButton.setOnClickListener {
                validateRoomName { roomName ->
                    performBlockingOperationWithDefaultErrorHandler(
                        getResult = { RoomsManager.create(roomName = roomName) },
                        onSuccess = {
                            navController.navigate(CreateRoomFragmentDirections.toWaitingRoom())
                        },
                    )
                }
            }
        }
    }

    private fun validateRoomName(onSuccess: (roomName: String) -> Unit) {
        val roomNameView = binding.roomNameView
        val roomName = roomNameView.text?.toString().orEmpty()
        when {
            roomName.isEmpty() || roomName.isBlank() ->
                roomNameView.error = getString(R.string.create_room_screen_empty_room_name_error)
            else -> onSuccess(roomName)
        }
    }
}
