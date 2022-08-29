package com.mobeedev.vama.album.domain.exception

abstract class VamaException : Exception() {
  abstract val originalException: Throwable?
}
