package com.denistuskenis.spyfall.ui.destinations.room

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class CivilRole(
    val role: String,
    val locationName: String,
) : Parcelable
