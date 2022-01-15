package com.denistuskenis.spyfall.remote

sealed class RemoteResult<out T> {

    data class Success<T>(val value: T) : RemoteResult<T>()

    object Error : RemoteResult<Nothing>()
}
