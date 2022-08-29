package com.mobeedev.vama.album.domain.error

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.onFailure(action: (exception: VamaError) -> Unit): Result<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    exceptionOrNull()?.let {
        if (it is VamaError) {
            action(it)
        }
    }
    return this
}

inline fun <T, R> T.runRecoverCatching(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(ErrorRegistry.handleErrorOrThrow(e))
    }
}