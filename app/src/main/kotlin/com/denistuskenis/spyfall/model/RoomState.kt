package com.denistuskenis.spyfall.model

sealed class RoomState {

    data class Waiting(
        val numberOfPlayers: Int,
        val numberOfReadyPlayers: Int,
    ) : RoomState()

    sealed class GameStarted : RoomState() {

        abstract val cardBackImageUrl: String

        data class AsSpy(
            override val cardBackImageUrl: String,
            val cardFrontImageUrl: String,
        ) : GameStarted()

        data class AsCivil(
            override val cardBackImageUrl: String,
            val role: String,
            val locationName: String,
            val locationImageUrl: String,
        ) : GameStarted()
    }
}
