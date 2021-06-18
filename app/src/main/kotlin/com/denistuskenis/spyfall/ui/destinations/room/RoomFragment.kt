package com.denistuskenis.spyfall.ui.destinations.room

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.denistuskenis.spyfall.ui.destinations.DestinationFragment
import com.denistuskenis.spyfall.ui.loadImage
import com.denistuskenis.spyfall.databinding.FragmentRoomBinding as ViewBinding

class RoomFragment : DestinationFragment<ViewBinding>(ViewBinding::inflate) {

    private val args: RoomFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val civilRole = args.civilRole

            cardLocationNameView.text = civilRole?.locationName
            cardLocationNameView.isVisible = civilRole != null
            cardRoleNameView.text = civilRole?.role
            cardRoleNameView.isVisible = civilRole != null

            cardView.setOnClickListener {
                cardView.flipTheView()
                cardHintView.isVisible = false
            }

            loadImage(imageUrl = args.cardBackImageUrl) { cardBackImage ->
                cardBackImageView.setImageDrawable(cardBackImage)

                loadImage(imageUrl = args.cardFrontImageUrl) { cardFrontImage ->
                    cardFrontImageView.setImageDrawable(cardFrontImage)
                    loadingIndicatorView.isVisible = false
                    cardView.isVisible = true
                    cardHintView.isVisible = true
                }
            }

            locationsReferenceButton.isInvisible = civilRole != null
            locationsReferenceButton.setOnClickListener {
                navController.navigate(RoomFragmentDirections.toLocationsReference())
            }
        }
    }
}
