package com.denistuskenis.spyfall.ui.destinations.room

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.denistuskenis.spyfall.R
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.databinding.FragmentRoomBinding as ViewBinding

class RoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    private val args: RoomFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val civilRole = args.civilRole

        binding.roleView.text = if (civilRole != null) {
            getString(
                R.string.room_screen_role_civil,
                civilRole.role,
                civilRole.locationName
            )
        } else getString(R.string.room_screen_role_spy)
    }
}
