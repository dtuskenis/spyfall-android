package com.denistuskenis.spyfall.model

import com.denistuskenis.spyfall.AppActivity
import com.denistuskenis.spyfall.backend.RemoteRoomsManager
import com.denistuskenis.spyfall.domain.*
import com.denistuskenis.spyfall.functional.Result
import com.denistuskenis.spyfall.functional.Success
import com.denistuskenis.spyfall.functional.UnknownError
import java.lang.Exception
import com.denistuskenis.spyfall.model.GameLocation as AppGameLocation

object RoomsManager {

    private val playerId get() = AppActivity.PLAYER_ID_PROVIDER.playerId

    suspend fun search(): Result<UnknownError, List<Room>> = wrapExceptions {
        RemoteRoomsManager.search().map { it.toAppRoom() }
    }

    suspend fun create(roomName: String): Result<UnknownError, Success> = wrapExceptions {
        RemoteRoomsManager.create(input = CreateRoomInput(roomName)).also {
            join(it)
        }.let { Success }
    }

    suspend fun join(roomId: String): Result<UnknownError, Boolean> = wrapExceptions {
        RemoteRoomsManager.join(input = JoinRoomInput(roomId = roomId, playerId = playerId)).let {
            it is JoinRoomResult.Success
        }
    }

    suspend fun check(): Result<UnknownError, RoomState?> = wrapExceptions {
        RemoteRoomsManager.check(input = CheckRoomInput(playerId))
            .toRoomState(imagesApiHost = RemoteRoomsManager.API_HOST)
    }

    suspend fun ready(): Result<UnknownError, Success> = wrapExceptions {
        RemoteRoomsManager.ready(input = ReadyPlayerInput(playerId)).let { Success }
    }

    suspend fun locations(): Result<UnknownError, List<AppGameLocation>> = wrapExceptions {
        RemoteRoomsManager.locations().map {
            it.toAppGameLocation(imagesApiHost = RemoteRoomsManager.API_HOST)
        }
    }

    private inline fun <Data> wrapExceptions(getData: () -> Data): Result<UnknownError, Data> {
        return try {
            Result.Success(getData())
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.Error(UnknownError)
        }
    }
}
