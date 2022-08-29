package com.mobeedev.vama.album.ui.details

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.mobeedev.vama.VamaAplication
import com.mobeedev.vama.album.domain.error.onFailure
import com.mobeedev.vama.album.domain.usecase.GetAlbumUseCase
import com.mobeedev.vama.album.ui.details.navigation.AlbumDetailsDestination
import com.mobeedev.vama.album.ui.model.AlbumItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AlbumDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    application: Application,
    private val getAlbumUseCase: GetAlbumUseCase
) : AndroidViewModel(application) {

    lateinit var albumId: String
//        checkNotNull( //todo configure state state handle in the future.
//        savedStateHandle[AlbumDetailsDestination.albumDetailsIdArg]
//    )

    private val _uiState: MutableStateFlow<AlbumDetailsScreenUiState> =
        MutableStateFlow(AlbumDetailsScreenUiState.Loading)
    val uiState: StateFlow<AlbumDetailsScreenUiState> = _uiState

    fun getAlbum() {
        getAlbumUseCase.invoke(params = GetAlbumUseCase.Params(albumId)) { result ->
            result.onSuccess {
                _uiState.value = AlbumDetailsScreenUiState.Success(it)
            }.onFailure { _uiState.value = AlbumDetailsScreenUiState.Error }
        }
    }

    fun openAlbum() {
        val state = uiState.value
        if (state is AlbumDetailsScreenUiState.Success) {
            val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(state.album.url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            getApplication<VamaAplication>().startActivity(openBrowserIntent)
        }
    }
}

sealed interface AlbumDetailsScreenUiState {
    data class Success(val album: AlbumItem) : AlbumDetailsScreenUiState
    object Error : AlbumDetailsScreenUiState
    object Loading : AlbumDetailsScreenUiState
}