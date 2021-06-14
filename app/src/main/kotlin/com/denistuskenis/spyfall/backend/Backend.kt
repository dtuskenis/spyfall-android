package com.denistuskenis.spyfall.backend

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObjectResponseResult
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import kotlinx.serialization.builtins.ListSerializer

object Backend {

    suspend fun search(): List<Room> {
        return Fuel.get("$API_HOST/search")
            .awaitObjectResponseResult(kotlinxDeserializerOf(ListSerializer(Room.serializer())))
            .let { (_, _, result) ->
                result.fold(
                    success = { it },
                    failure = { emptyList() }
                )
            }
    }

    const val API_HOST = "https://api-spyfall.herokuapp.com"
}
