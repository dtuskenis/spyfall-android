package com.denistuskenis.spyfall.ui.destinations.join

import android.os.Bundle
import android.view.View
import androidx.lifecycle.coroutineScope
import com.denistuskenis.spyfall.databinding.ItemRoomBinding
import com.denistuskenis.spyfall.model.Room
import com.denistuskenis.spyfall.model.RoomsManager
import com.denistuskenis.spyfall.ui.adapter.ReusableListAdapter
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.databinding.FragmentJoinBinding as ViewBinding

class JoinRoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    private val roomsAdapter = ReusableListAdapter(
        bindData = { room: Room, binding ->
            binding.roomNameView.text = room.name
            binding.root.setOnClickListener {
                navController.navigate(JoinRoomFragmentDirections.toWaitingRoom())
            }
        },
        inflateBinding = ItemRoomBinding::inflate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            roomsView.adapter = roomsAdapter
        }

        lifecycle.coroutineScope.launchWhenStarted {
            RoomsManager.search().also(roomsAdapter::submitList)
        }
    }
}
