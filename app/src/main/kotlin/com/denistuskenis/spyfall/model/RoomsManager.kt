package com.denistuskenis.spyfall.model

import com.denistuskenis.spyfall.backend.Backend
import com.denistuskenis.spyfall.domain.CreateRoomInput
import com.denistuskenis.spyfall.domain.JoinRoomInput
import com.denistuskenis.spyfall.domain.JoinRoomResult
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
}
