package com.denistuskenis.spyfall.model

import android.content.Context
import com.denistuskenis.spyfall.domain.PlayerId
import java.util.UUID

class PlayerIdProvider(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    val playerId: PlayerId =
        sharedPreferences.getString(SHARED_PREFERENCES_PLAYER_ID_KEY, null)
            ?: sharedPreferences.edit().let {
                val generatedPlayerId = UUID.randomUUID().toString()
                it.putString(SHARED_PREFERENCES_PLAYER_ID_KEY, generatedPlayerId)
                it.apply()
                generatedPlayerId
            }

    private companion object {
        const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"
        const val SHARED_PREFERENCES_PLAYER_ID_KEY = "SHARED_PREFERENCES_PLAYER_ID_KEY"
    }
}
