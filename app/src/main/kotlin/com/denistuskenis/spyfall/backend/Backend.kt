package com.denistuskenis.spyfall.backend

import com.denistuskenis.spyfall.domain.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitObjectResponseResult
import com.github.kittinunf.fuel.coroutines.awaitString
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Backend: RoomsManager {

    private val json = Json { }

    override suspend fun search(): List<Room> {
        return Fuel.get("$API_HOST/search")
            .awaitObjectResponseResult(kotlinxDeserializerOf(ListSerializer(Room.serializer())))
            .let { (_, _, result) ->
                result.fold(
                    success = { it },
                    failure = { emptyList() }
                )
            }
    }

    override suspend fun create(input: CreateRoomInput): RoomId {
        return Fuel.post("$API_HOST/create")
            .jsonBody(json.encodeToString(input))
            .awaitString()
    }

    override suspend fun join(input: JoinRoomInput): JoinRoomResult {
        return Fuel.post("$API_HOST/join")
            .jsonBody(json.encodeToString(input))
            .awaitObjectResponseResult<JoinRoomResult>(kotlinxDeserializerOf())
            .let { (_, _, result) -> result.get() }
    }

    private const val API_HOST = "https://api-spyfall.herokuapp.com"
}
