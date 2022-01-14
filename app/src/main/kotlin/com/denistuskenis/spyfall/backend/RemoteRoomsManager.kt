package com.denistuskenis.spyfall.backend

import com.denistuskenis.spyfall.domain.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitObjectResponseResult
import com.github.kittinunf.fuel.coroutines.awaitUnit
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object RemoteRoomsManager : RoomsManager {

    private val json = Json

    override suspend fun search(): List<Room> =
        get("/search").await(ListSerializer(Room.serializer()))

    override suspend fun create(input: CreateRoomInput): RoomId =
        post("/create", input).await(RoomId.serializer())

    override suspend fun join(input: JoinRoomInput): JoinRoomResult =
        post("/join", input).await(JoinRoomResult.serializer())

    override suspend fun check(input: CheckRoomInput): CheckRoomResult =
        post("/check", input).await(CheckRoomResult.serializer())

    override suspend fun ready(input: ReadyPlayerInput) =
        post("/ready", input).awaitUnit()

    override suspend fun locations(): List<GameLocation> =
        get("/locations").await(ListSerializer(GameLocation.serializer()))

    private fun get(path: String): Request =
        Fuel.get("$API_HOST$path")

    private inline fun <reified Body> post(path: String, body: Body): Request =
        Fuel.post("$API_HOST$path")
            .jsonBody(json.encodeToString(body))

    private suspend inline fun <reified T : Any> Request.await(
        deserializationStrategy: DeserializationStrategy<T>
    ): T = awaitObjectResponseResult(kotlinxDeserializerOf(deserializationStrategy))
        .let { (_, _, result) -> result.get() }

    const val API_HOST = "https://api-spyfall.herokuapp.com"
}
