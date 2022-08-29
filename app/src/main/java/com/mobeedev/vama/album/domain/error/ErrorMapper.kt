package com.mobeedev.vama.album.domain.error

import com.mobeedev.vama.album.domain.exception.VamaApiException
import retrofit2.HttpException
import java.net.HttpURLConnection

/**
 * Add all error mappers here or in specific modules
 */
internal fun VamaApiException.toBlockFiError(): VamaError = when {
    this.code == HttpURLConnection.HTTP_UNAUTHORIZED -> CoreError.Auth(this)
    this.code == HttpURLConnection.HTTP_NO_CONTENT -> CoreError.NoBody(this)
    this.code == HttpURLConnection.HTTP_BAD_METHOD -> CoreError.ServerError(this)
    this.code == HttpURLConnection.HTTP_BAD_REQUEST -> CoreError.ServerError(this)

    this.code >= HttpURLConnection.HTTP_INTERNAL_ERROR -> CoreError.ServerError(this)
    this.code >= HttpURLConnection.HTTP_UNAVAILABLE -> CoreError.ServiceUnavailable()
    else -> CoreError.ServerError(this)
}

internal fun HttpException.toBlockFiError(): VamaError = when (code()) {
    HttpURLConnection.HTTP_FORBIDDEN, HttpURLConnection.HTTP_UNAUTHORIZED -> CoreError.Auth()
    HttpURLConnection.HTTP_UNAVAILABLE -> CoreError.ServiceUnavailable()
    else -> CoreError.Unknown()
}
