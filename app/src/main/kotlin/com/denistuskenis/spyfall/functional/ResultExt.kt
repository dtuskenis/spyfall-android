package com.denistuskenis.spyfall.functional

import com.denistuskenis.spyfall.remote.RemoteResult

inline fun <E, T, R> Result<E, T>.mapSuccess(transform: (T) -> R): Result<E, R> =
    when (this) {
        is Result.Success -> Result.Success(transform(this.value))
        is Result.Error -> this
    }

inline fun <E, T, R> Result<E, T>.flatMapSuccess(transform: (T) -> Result<E, R>): Result<E, R> =
    when (this) {
        is Result.Success -> transform(this.value)
        is Result.Error -> this
    }

fun <T, R> RemoteResult<T>.toResult(transform: (T) -> R): Result<UnknownError, R> =
    when (this) {
        is RemoteResult.Success -> Result.Success(transform(this.value))
        is RemoteResult.Error -> Result.Error(UnknownError)
    }
