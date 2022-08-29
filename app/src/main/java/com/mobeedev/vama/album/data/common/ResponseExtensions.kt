package com.mobeedev.vama.album.data.common

import com.mobeedev.vama.album.domain.exception.VamaApiException
import retrofit2.Response

fun <A : Any> Response<A>.bodyOrException(): A {
    val body = body()
    return if (isSuccessful && body != null) {
        body
    } else {
        throw VamaApiException(this)
    }
}