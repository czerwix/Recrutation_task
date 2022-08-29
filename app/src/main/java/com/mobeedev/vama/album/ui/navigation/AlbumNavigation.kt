package com.mobeedev.vama.album.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mobeedev.vama.album.ui.AlbumRoute
import com.mobeedev.vama.navigation.VamaNavigationDestination

object AlbumDestination : VamaNavigationDestination {
    override val route = "album_route"
    override val destination = "album_destination"
}

fun NavGraphBuilder.albumGraph(
    navigateToAlbum: (String) -> Unit,
) {
    composable(route = AlbumDestination.route) {
        AlbumRoute(navigateToAlbum = navigateToAlbum)
    }
}