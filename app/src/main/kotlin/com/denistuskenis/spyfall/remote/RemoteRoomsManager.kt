package com.denistuskenis.spyfall.remote

import com.denistuskenis.spyfall.domain.CheckRoomInput
import com.denistuskenis.spyfall.domain.CheckRoomResult
import com.denistuskenis.spyfall.domain.CreateRoomInput
import com.denistuskenis.spyfall.domain.GameLocation
import com.denistuskenis.spyfall.domain.JoinRoomInput
import com.denistuskenis.spyfall.domain.JoinRoomResult
import com.denistuskenis.spyfall.domain.ReadyPlayerInput
import com.denistuskenis.spyfall.domain.Room
import com.denistuskenis.spyfall.domain.RoomId
import com.denistuskenis.spyfall.domain.RoomsManager
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
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

object RemoteRoomsManager {

    suspend fun search(): RemoteResult<List<Room>> =
        catchFuelError { FuelRoomsManager.search() }

    suspend fun create(input: CreateRoomInput): RemoteResult<RoomId> =
        catchFuelError { FuelRoomsManager.create(input) }

    suspend fun join(input: JoinRoomInput): RemoteResult<JoinRoomResult> =
        catchFuelError { FuelRoomsManager.join(input) }

    suspend fun check(input: CheckRoomInput): RemoteResult<CheckRoomResult> =
        catchFuelError { FuelRoomsManager.check(input) }

    suspend fun ready(input: ReadyPlayerInput): RemoteResult<Unit> =
        catchFuelError { FuelRoomsManager.ready(input) }

    suspend fun locations(): RemoteResult<List<GameLocation>> =
        catchFuelError { FuelRoomsManager.locations() }

    private inline fun <T> catchFuelError(call: () -> T): RemoteResult<T> =
        try {
            RemoteResult.Success(call())
        } catch (error: FuelError) {
            RemoteResult.Error
        }

    private object FuelRoomsManager : RoomsManager {

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
                .jsonBody(Json.encodeToString(body))

        private suspend inline fun <reified T : Any> Request.await(
            deserializationStrategy: DeserializationStrategy<T>
        ): T = awaitObjectResponseResult(kotlinxDeserializerOf(deserializationStrategy))
            .let { (_, _, result) -> result.get() }
    }

    const val API_HOST = "https://api-spyfall.herokuapp.com"
}
