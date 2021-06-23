package com.denistuskenis.spyfall.model

import com.denistuskenis.spyfall.domain.CheckRoomResult as DomainCheckRoomResult
import com.denistuskenis.spyfall.domain.GameLocation as DomainGameLocation
import com.denistuskenis.spyfall.domain.Room as DomainRoom
import com.denistuskenis.spyfall.model.GameLocation as AppGameLocation
import com.denistuskenis.spyfall.model.Room as AppRoom
import com.denistuskenis.spyfall.model.RoomState as AppRoomState

fun DomainRoom.toAppRoom(): AppRoom = AppRoom(
    id = this.id,
    name = this.name,
)

fun DomainCheckRoomResult.toRoomState(imagesApiHost: String): AppRoomState? = when (this) {
    is DomainCheckRoomResult.Waiting -> AppRoomState.Waiting(
        numberOfPlayers = this.numberOfPlayers,
        numberOfReadyPlayers = this.numberOfReadyPlayers,
    )
    is DomainCheckRoomResult.GameStarted.AsSpy -> AppRoomState.GameStarted.AsSpy(
        cardBackImageUrl = "$imagesApiHost${this.cardBackImagePath}",
        cardFrontImageUrl = "$imagesApiHost${this.cardFrontImagePath}",
    )
    is DomainCheckRoomResult.GameStarted.AsCivil -> AppRoomState.GameStarted.AsCivil(
        cardBackImageUrl = "$imagesApiHost${this.cardBackImagePath}",
        role = this.role,
        locationName = this.locationName,
        locationImageUrl = "$imagesApiHost${this.locationImagePath}",
    )
    is DomainCheckRoomResult.PlayerNotInRoom -> null
}

fun DomainGameLocation.toAppGameLocation(imagesApiHost: String): AppGameLocation = AppGameLocation(
    name = this.name,
    imageUrl = "$imagesApiHost${this.imagePath}",
)
