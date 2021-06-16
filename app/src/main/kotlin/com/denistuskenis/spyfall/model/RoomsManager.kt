package com.denistuskenis.spyfall.model

import com.denistuskenis.spyfall.backend.Backend
import com.denistuskenis.spyfall.domain.*
import java.util.*
import com.denistuskenis.spyfall.model.Room as AppRoom

object RoomsManager {

    private val playerId = UUID.randomUUID().toString()

    suspend fun search(): List<AppRoom> =
        Backend.search().map {
            AppRoom(
                id = it.id,
                name = it.name,
            )
        }

    suspend fun create(roomName: String) =
        Backend.create(
            input = CreateRoomInput(
                roomName = roomName
            )
        ).also { join(it) }

    suspend fun join(roomId: String): Boolean =
        Backend.join(
            input = JoinRoomInput(
                roomId = roomId,
                playerId = playerId,
            )
        ).let {
            it is JoinRoomResult.Success
        }

    suspend fun check(): RoomState? {
        return Backend.check(input = CheckRoomInput(playerId))
            .let {
                when (it) {
                    is CheckRoomResult.Waiting -> RoomState.Waiting(
                        numberOfPlayers = it.numberOfPlayers,
                        numberOfReadyPlayers = it.numberOfReadyPlayers,
                    )
                    is CheckRoomResult.GameStarted.AsSpy -> RoomState.GameStarted.AsSpy
                    is CheckRoomResult.GameStarted.AsCivil -> RoomState.GameStarted.AsCivil(
                        role = it.role,
                        locationName = it.locationName,
                    )
                    is CheckRoomResult.PlayerNotInRoom -> null
                }
            }
    }

    suspend fun ready() {
        Backend.ready(input = ReadyPlayerInput(playerId))
    }
}
