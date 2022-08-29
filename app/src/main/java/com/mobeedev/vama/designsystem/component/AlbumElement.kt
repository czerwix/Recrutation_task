package com.mobeedev.vama.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mobeedev.vama.album.ui.model.AlbumItem
import com.mobeedev.vama.common.extensions.empty
import com.mobeedev.vama.designsystem.theme.Gray
import com.mobeedev.vama.designsystem.theme.VamaRecruTheme

@Composable
fun AlbumElement(albumItem: AlbumItem,onClick: () -> Unit) {
    VamaRecruTheme {
        Box(
            modifier = Modifier
                .height(173.dp)
                .width(173.dp)
                .clickable {onClick()  }
        ) {
            AsyncImage(
                //todo quick hack for image size, replace with apropiate API call in the future
                model = albumItem.artworkUrl100?.replace("100x100", "300x300"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = albumItem.name ?: String.empty,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    letterSpacing = (-0.04).sp,
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = albumItem.artistName ?: String.empty,
                    color = Gray,
                    modifier = Modifier.padding(start = 12.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewAlbumElement() {
    VamaRecruTheme {
        Surface {
            AlbumElement(
                AlbumItem(
                    name = "The best Album!!!!!!!!",
                    artistName = "Szymon Kraus",
                    id = "RANDOM_ID"
                ),
                onClick = {}
            )
        }
    }
}