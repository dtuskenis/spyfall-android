package com.denistuskenis.spyfall.model

import com.denistuskenis.spyfall.AppActivity
import com.denistuskenis.spyfall.domain.CheckRoomInput
import com.denistuskenis.spyfall.domain.CreateRoomInput
import com.denistuskenis.spyfall.domain.FindRoomInput
import com.denistuskenis.spyfall.domain.JoinRoomInput
import com.denistuskenis.spyfall.domain.JoinRoomResult
import com.denistuskenis.spyfall.domain.ReadyPlayerInput
import com.denistuskenis.spyfall.functional.Result
import com.denistuskenis.spyfall.functional.Success
import com.denistuskenis.spyfall.functional.UnknownError
import com.denistuskenis.spyfall.functional.flatMapSuccess
import com.denistuskenis.spyfall.functional.mapSuccess
import com.denistuskenis.spyfall.functional.toResult
import com.denistuskenis.spyfall.remote.RemoteRoomsManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.denistuskenis.spyfall.model.GameLocation as AppGameLocation

object RoomsManager {

    private val playerId get() = AppActivity.PLAYER_ID_PROVIDER.playerId

    suspend fun search(): Result<UnknownError, List<Room>> =
        RemoteRoomsManager.search()
            .toResult { rooms -> rooms.map { it.toAppRoom() } }

    suspend fun find(): Result<UnknownError, Success> =
        RemoteRoomsManager.find(input = FindRoomInput(playerId = playerId))
            .toResult { it }
            .mapSuccess { Success }

    suspend fun create(roomName: String): Result<UnknownError, Success> =
        RemoteRoomsManager.create(input = CreateRoomInput(roomName))
            .toResult { it }
            .flatMapSuccess { join(it) }
            .mapSuccess { Success }

    suspend fun join(roomId: String): Result<UnknownError, Boolean> =
        RemoteRoomsManager.join(input = JoinRoomInput(roomId = roomId, playerId = playerId))
            .toResult { it is JoinRoomResult.Success }

    suspend fun check(): Flow<Result<UnknownError, RoomState?>> = flow {
        while (true) {
            emit(
                RemoteRoomsManager.check(input = CheckRoomInput(playerId))
                    .toResult { it.toRoomState(imagesApiHost = RemoteRoomsManager.API_HOST) }
            )
            delay(CHECK_ROOM_POLLING_INTERVAL_MILLISECONDS)
        }
    }

    suspend fun ready(): Result<UnknownError, Success> =
        RemoteRoomsManager.ready(input = ReadyPlayerInput(playerId))
            .toResult { Success }

    suspend fun locations(): Result<UnknownError, List<AppGameLocation>> =
        RemoteRoomsManager.locations()
            .toResult { locations ->
                locations.map { it.toAppGameLocation(imagesApiHost = RemoteRoomsManager.API_HOST) }
            }

    private const val CHECK_ROOM_POLLING_INTERVAL_MILLISECONDS = 500L
}
