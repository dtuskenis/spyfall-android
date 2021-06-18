package com.denistuskenis.spyfall.functional

sealed class Result<out Error, out Success> {

    data class Success<Success>(
        val value: Success,
    ) : Result<Nothing, Success>()

    data class Error<Error>(
        val error: Error,
    ) : Result<Error, Nothing>()
}
