package com.mobeedev.vama.album.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobeedev.vama.album.domain.error.onFailure
import com.mobeedev.vama.album.domain.usecase.GetSavedTopAlbumDataUseCase
import com.mobeedev.vama.album.domain.usecase.LoadTopAlbumUseCase
import com.mobeedev.vama.album.ui.model.AlbumItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AlbumViewModel(
    application: Application,
    private val getSavedTopAlbumDataUseCase: GetSavedTopAlbumDataUseCase,
    private val loadTopAlbumUseCase: LoadTopAlbumUseCase
) : AndroidViewModel(application) {

    private val _uiState: MutableStateFlow<AlbumScreenUiState> =
        MutableStateFlow(AlbumScreenUiState.Loading)
    val uiState: StateFlow<AlbumScreenUiState> = _uiState

    fun loadData() {
        getSavedTopAlbumDataUseCase.invoke { result ->
            result.onSuccess {
                if (it.isNotEmpty()) {
                    _uiState.value = AlbumScreenUiState.Success(it)
                }
                refreshOnlineData()
            }.onFailure {
                //todo handle errors
            }
        }
    }

    fun refreshOnlineData() {
        loadTopAlbumUseCase.invoke { result ->
            result.onSuccess {
                _uiState.value = AlbumScreenUiState.Success(it)
            }.onFailure {
                //todo handle error
            }
        }
    }
}

sealed interface AlbumScreenUiState {
    data class Success(val topAlumList: List<AlbumItem>) : AlbumScreenUiState
    object Error : AlbumScreenUiState
    object Loading : AlbumScreenUiState
}