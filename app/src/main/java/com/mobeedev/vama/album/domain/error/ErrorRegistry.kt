package com.mobeedev.vama.album.domain.error

import android.util.Log
import com.mobeedev.vama.album.domain.exception.VamaApiException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass

class ErrorRegistry<T : Throwable> private constructor(
  private val errorType: KClass<T>,
  private val mapper: (T) -> VamaError
) {
  private lateinit var throwable: T

  fun setThrowable(throwable: Throwable) {
    this.throwable = throwable as T
  }

  fun toBlockFiError(): VamaError = mapper.invoke(throwable)

  companion object {
    private val errorHandlers = mutableMapOf<String, ErrorRegistry<*>>()

    /**
     * register all errors to handle and actions here, or add dynamically by DI
     */
    init {
      registerErrorHandler(VamaError::class) { it }
      registerErrorHandler(VamaApiException::class) { it.toBlockFiError() }
      registerErrorHandler(IOException::class) { CoreError.Network() }
      registerErrorHandler(UnknownHostException::class) { CoreError.Network() }

      registerErrorHandler(SocketTimeoutException::class) { CoreError.ServiceUnavailable() }

      registerErrorHandler(HttpException::class) { it.toBlockFiError() }
    }

    /**
     * Register error here, remember that subclasses have to be registered separately
     * So, instead of registering IOException register UnknownHostException
     */
    fun <T : Throwable> registerErrorHandler(
      errorType: KClass<T>,
      mapper: (T) -> VamaError
    ) {
      val handler = ErrorRegistry(errorType, mapper)
      val throwableClassName =
        handler.errorType.simpleName ?: error("SimpleName is null")
      if (errorHandlers.containsKey(throwableClassName)) {
        error("Error handler for $throwableClassName already registered")
      } else {
        errorHandlers[throwableClassName] = handler
      }
    }

    fun handleErrorOrThrow(throwable: Throwable): VamaError {
      if (throwable is VamaError) return throwable
      val handler = errorHandlers[throwable::class.simpleName]
        ?: return CoreError.Unknown(throwable.also {
          Log.e(
            "ErrorHandler",
            "Unable to handle error",
            it
          )
        })

      handler.setThrowable(throwable)
      return handler.toBlockFiError().also { Log.e("ErrorHandler", "Error handled", it) }
    }
  }
}
