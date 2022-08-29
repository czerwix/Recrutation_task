package com.mobeedev.vama.album.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobeedev.vama.R
import com.mobeedev.vama.album.ui.model.AlbumItem
import com.mobeedev.vama.designsystem.component.AlbumElement
import com.mobeedev.vama.designsystem.component.VamaLoadingWheel
import com.mobeedev.vama.designsystem.theme.VamaRecruTheme
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AlbumRoute(
    modifier: Modifier = Modifier,
    viewModel: AlbumViewModel = getViewModel(),
    navigateToAlbum: (String) -> Unit
) {
    val uiState: AlbumScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()

    AlbumsScreen(
        albumState = uiState,
        modifier = modifier,
        navigateToAlbum = navigateToAlbum
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@VisibleForTesting
@Composable
internal fun AlbumsScreen(
    albumState: AlbumScreenUiState,
    modifier: Modifier = Modifier,
    navigateToAlbum: (String) -> Unit
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        when (albumState) {
            AlbumScreenUiState.Error -> TODO()
            AlbumScreenUiState.Loading ->
                VamaLoadingWheel(
                    modifier = modifier,
                    contentDesc = stringResource(id = R.string.album_loading),
                )
            is AlbumScreenUiState.Success -> {
                LazyVerticalGrid(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    albumCards(albumState.topAlumList, navigateToAlbum)
                }
            }
        }
    }
}

private fun LazyGridScope.albumCards(
    topAlumList: List<AlbumItem>,
    navigateToAlbum: (String) -> Unit
) {
    items(topAlumList) { item ->
        AlbumElement(albumItem = item) { navigateToAlbum(item.id) }
    }
}

@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Composable
fun AlbumScreenPopulated() {
    VamaRecruTheme() {
        AlbumsScreen(
            albumState = AlbumScreenUiState.Success(
                mutableListOf(
                    AlbumItem(
                        artistName = "Szymon Kraus",
                        name = "something amazing!!!",
                        id = "1"
                    ),
                    AlbumItem(
                        artistName = "Szymon Krauss",
                        name = "something amazing!!!",
                        id = "2"
                    ),
                    AlbumItem(
                        artistName = "Szymon Krausss",
                        name = "something amazing!!!",
                        id = "3"
                    ), AlbumItem(
                        artistName = "Szymon Krausssss",
                        name = "something amazing!!!",
                        id = "4"
                    )
                )
            ),
            navigateToAlbum = {}
        )
    }
}

@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Composable
fun AlbumScreenLoading() {
    VamaRecruTheme {
        AlbumsScreen(
            albumState = AlbumScreenUiState.Loading,
            navigateToAlbum = {}
        )
    }
}


















