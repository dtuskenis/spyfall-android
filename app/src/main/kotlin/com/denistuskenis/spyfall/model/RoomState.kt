package com.denistuskenis.spyfall.model

sealed class RoomState {

    data class Waiting(
        val numberOfPlayers: Int,
        val numberOfReadyPlayers: Int,
    ) : RoomState()

    sealed class GameStarted : RoomState() {

        object AsSpy : GameStarted()

        data class AsCivil(
            val role: String,
            val locationName: String,
        ) : GameStarted()
    }
}
