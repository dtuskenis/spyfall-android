package com.denistuskenis.spyfall.ui.destinations.room

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CivilRole(
    val role: String,
    val locationName: String,
) : Parcelable
