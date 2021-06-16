package com.denistuskenis.spyfall.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CheckRoomResult {

    @Serializable
    @SerialName("Waiting")
    data class Waiting(
        val numberOfPlayers: Int,
        val numberOfReadyPlayers: Int,
    ) : CheckRoomResult()

    @Serializable
    sealed class GameStarted : CheckRoomResult() {

        @Serializable
        @SerialName("AsSpy")
        object AsSpy : GameStarted()

        @Serializable
        @SerialName("AsCivil")
        data class AsCivil(
            val role: String,
            val locationName: String,
        ) : GameStarted()
    }

    @Serializable
    @SerialName("PlayerNotInRoom")
    object PlayerNotInRoom : CheckRoomResult()
}
