package com.denistuskenis.spyfall.model

import com.denistuskenis.spyfall.backend.Backend
import com.denistuskenis.spyfall.model.Room as DomainRoom

object RoomsManager {

    suspend fun search(): List<DomainRoom> =
        Backend.search().map { DomainRoom(name = it.id) }
}
