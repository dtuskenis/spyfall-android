package com.denistuskenis.spyfall.ui.destinations.join

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.databinding.FragmentJoinBinding
import com.denistuskenis.spyfall.databinding.ItemRoomBinding
import com.denistuskenis.spyfall.model.Room
import com.denistuskenis.spyfall.model.SAMPLE_ROOMS
import com.denistuskenis.spyfall.ui.adapter.ReusableListAdapter

class JoinRoomFragment: Fragment(R.layout.fragment_join) {

    private val roomsAdapter = ReusableListAdapter(
        bindData = { room: Room, binding ->
            binding.roomNameView.text = room.name
            binding.root.isClickable = true
        },
        inflateBinding = ItemRoomBinding::inflate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentJoinBinding.bind(view)

        with(binding) {
            roomsView.adapter = roomsAdapter
        }

        roomsAdapter.submitList(SAMPLE_ROOMS)
    }
}
