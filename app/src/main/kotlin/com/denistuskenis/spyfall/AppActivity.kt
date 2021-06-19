package com.denistuskenis.spyfall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denistuskenis.spyfall.model.PlayerIdProvider

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        PLAYER_ID_PROVIDER = PlayerIdProvider(context = this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        lateinit var PLAYER_ID_PROVIDER: PlayerIdProvider
    }
}
