package com.mobeedev.vama.album.ui.details

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mobeedev.vama.R
import com.mobeedev.vama.album.ui.model.AlbumItem
import com.mobeedev.vama.album.ui.model.GenreItem
import com.mobeedev.vama.common.extensions.empty
import com.mobeedev.vama.designsystem.component.VamaFilledButton
import com.mobeedev.vama.designsystem.component.VamaOutlinedButton
import com.mobeedev.vama.designsystem.theme.Blue
import com.mobeedev.vama.designsystem.theme.Gray
import com.mobeedev.vama.designsystem.theme.VamaRecruTheme
import com.mobeedev.vama.designsystem.theme.White
import org.koin.androidx.compose.getStateViewModel
import java.time.LocalDate

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun albumDetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumDetailsViewModel = getStateViewModel(),
    albumId: String,
) {
    viewModel.apply {
        this.albumId = albumId
        getAlbum()
    }
    val uiState: AlbumDetailsScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()

    albumsDetailsScreen(
        albumState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onOpenAlbumClick = { viewModel.openAlbum() })
}

@VisibleForTesting
@Composable
internal fun albumsDetailsScreen(
    albumState: AlbumDetailsScreenUiState,
    onBackClick: () -> Unit,
    onOpenAlbumClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (albumState) {
        AlbumDetailsScreenUiState.Error -> Unit
        AlbumDetailsScreenUiState.Loading -> Unit
        is AlbumDetailsScreenUiState.Success -> albumDetailsCard(
            albumItem = albumState.album,
            modifier = modifier,
            onBackClick = onBackClick,
            onOpenAlbumClick = onOpenAlbumClick
        )

    }

}

@VisibleForTesting
@Composable
internal fun albumDetailsCard(
    albumItem: AlbumItem,
    onBackClick: () -> Unit,
    onOpenAlbumClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 60.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp)
            ) {
                AsyncImage(
                    //todo quick hack for image size, replace with apropiate API call in the future
                    model = albumItem.artworkUrl100?.replace("100x100", "500x500"),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(390.dp)
                        .clickable { onBackClick() }
                )

                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_icon_chevron_left),
                        contentDescription = null,
                        modifier = Modifier
                            .width(13.dp)
                            .height(19.dp)
                            .padding(end = 2.dp)
                    )
                }
            }
            Text(
                text = albumItem.artistName ?: String.empty,
                color = Gray,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp)
            )
            Text(
                text = albumItem.name ?: String.empty,
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                letterSpacing = (-0.04).sp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                albumItem.genres.forEach {
                    VamaOutlinedButton(
                        onClick = { },
                        small = false,
                        enabled = false,
                        modifier = Modifier.padding(start = 6.dp)
                    ) {
                        Text(text = it.name ?: String.empty, color = Blue, fontSize = 12.sp)
                    }
                }
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var parsedDate = String.empty
            try {
                val localDate = LocalDate.parse(albumItem.releaseDate)

                parsedDate =
                    "${
                        localDate.month.name.lowercase().capitalize()

                    } ${localDate.dayOfMonth}, ${localDate.year}" //todo extract to SimpleDateTimeFormat
            } catch (e: Exception) {
            }

            Text(
                text = "Released $parsedDate Copyright 2022\n Apple Inc. All rights reserved.",//todo extract string to strings
                color = Gray,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            VamaFilledButton(
                onClick = { onOpenAlbumClick() },
                small = false
            ) {
                Text(
                    text = stringResource(id = R.string.visit_the_album),
                    fontSize = 16.sp,
                    color = White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAlbumDetailsScreen() {
    VamaRecruTheme {
        Surface {
            albumDetailsCard(
                AlbumItem(
                    name = "The best Album!!!!!!!!",
                    artistName = "Szymon Kraus",
                    id = "RANDOM_ID",
                    releaseDate = "2022-08-26",
                    genres = mutableListOf(
                        GenreItem("1", "rock"),
                        GenreItem("2", "techno"),
                        GenreItem("3", "hardcore")
                    )
                ),
                onBackClick = {},
                onOpenAlbumClick = {}
            )
        }
    }
}