package com.mobeedev.vama.album.domain.exception

import com.mobeedev.vama.album.domain.exception.VamaException
import retrofit2.Response

data class VamaApiException(
  val code: Int,
  val url: String,
  override val message: String?,
  override val originalException: Throwable? = null
) : VamaException() {

  // TODO [SK] Rebuild this to parse errorBody ass well when error codes are standardised
  constructor(response: Response<*>) : this(
    response.code(),
    response.raw().request.url.toString(),
    response.errorBody()?.string()
  )

  override fun toString() = "CODE: $code \nURL: $url \nMESSAGE: $message"
}
