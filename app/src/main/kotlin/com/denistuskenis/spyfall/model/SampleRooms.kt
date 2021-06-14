package com.denistuskenis.spyfall.model

import java.util.UUID

val SAMPLE_ROOMS: List<Room> = generateSequence {
    UUID.randomUUID().toString()
}.map(::Room).take(10).toList()
