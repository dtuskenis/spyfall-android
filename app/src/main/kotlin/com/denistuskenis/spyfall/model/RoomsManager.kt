package com.denistuskenis.spyfall.model

import com.denistuskenis.spyfall.AppActivity
import com.denistuskenis.spyfall.backend.Backend
import com.denistuskenis.spyfall.domain.*
import com.denistuskenis.spyfall.functional.Result
import com.denistuskenis.spyfall.functional.Success
import com.denistuskenis.spyfall.functional.UnknownError
import java.lang.Exception
import com.denistuskenis.spyfall.model.GameLocation as AppGameLocation
import com.denistuskenis.spyfall.model.Room as AppRoom

object RoomsManager {

    private val playerId get() = AppActivity.PLAYER_ID_PROVIDER.playerId

    suspend fun search(): Result<UnknownError, List<AppRoom>> = wrapExceptions {
        Backend.search().map {
            AppRoom(
                id = it.id,
                name = it.name,
            )
        }
    }

    suspend fun create(roomName: String): Result<UnknownError, Success> = wrapExceptions {
        Backend.create(
            input = CreateRoomInput(
                roomName = roomName
            )
        ).also {
            join(it)
        }.let { Success }
    }

    suspend fun join(roomId: String): Result<UnknownError, Boolean> = wrapExceptions {
        Backend.join(
            input = JoinRoomInput(
                roomId = roomId,
                playerId = playerId,
            )
        ).let {
            it is JoinRoomResult.Success
        }
    }

    suspend fun check(): Result<UnknownError, RoomState?> = wrapExceptions {
        Backend.check(input = CheckRoomInput(playerId))
            .let {
                when (it) {
                    is CheckRoomResult.Waiting -> RoomState.Waiting(
                        numberOfPlayers = it.numberOfPlayers,
                        numberOfReadyPlayers = it.numberOfReadyPlayers,
                    )
                    is CheckRoomResult.GameStarted.AsSpy -> RoomState.GameStarted.AsSpy(
                        cardBackImageUrl = "${Backend.API_HOST}${it.cardBackImagePath}",
                        cardFrontImageUrl = "${Backend.API_HOST}${it.cardFrontImagePath}",
                    )
                    is CheckRoomResult.GameStarted.AsCivil -> RoomState.GameStarted.AsCivil(
                        cardBackImageUrl = "${Backend.API_HOST}${it.cardBackImagePath}",
                        role = it.role,
                        locationName = it.locationName,
                        locationImageUrl = "${Backend.API_HOST}${it.locationImagePath}",
                    )
                    is CheckRoomResult.PlayerNotInRoom -> null
                }
            }
    }

    suspend fun ready(): Result<UnknownError, Success> = wrapExceptions {
        Backend.ready(input = ReadyPlayerInput(playerId)).let { Success }
    }

    suspend fun locations(): Result<UnknownError, List<AppGameLocation>> = wrapExceptions {
        Backend.locations().map {
            AppGameLocation(
                name = it.name,
                imageUrl = "${Backend.API_HOST}${it.imagePath}",
            )
        }
    }

    private inline fun <Data> wrapExceptions(getData: () -> Data): Result<UnknownError, Data> {
        try {
            return Result.Success(getData())
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Result.Error(UnknownError)
        }
    }
}
