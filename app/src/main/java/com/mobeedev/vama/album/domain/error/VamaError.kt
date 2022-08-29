package com.mobeedev.vama.album.domain.error

import com.mobeedev.vama.album.domain.exception.VamaException

abstract class VamaError : VamaException()

sealed class CoreError : VamaError() {
  data class Network(override val originalException: Throwable? = null) : CoreError()
  data class ServerError(override val originalException: Throwable? = null) : CoreError()
  data class ServiceUnavailable(override val originalException: Throwable? = null) : CoreError()
  data class NoBody(override val originalException: Throwable? = null) : CoreError()

  data class Auth(override val originalException: Throwable? = null) : CoreError()

  data class Unknown(override val originalException: Throwable? = null) : CoreError()
  data class ErrorWithDetail(val errorMsg: String, override val originalException: Throwable) : CoreError()
}

sealed class DataErrors : VamaError(){
  data class NoData(override val originalException: Throwable?= null) : DataErrors()
}