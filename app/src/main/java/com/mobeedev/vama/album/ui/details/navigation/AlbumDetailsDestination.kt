package com.mobeedev.vama.album.ui.details.navigation

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mobeedev.vama.album.ui.details.albumDetailsRoute
import com.mobeedev.vama.navigation.VamaNavigationDestination

object AlbumDetailsDestination : VamaNavigationDestination {
    const val albumDetailsIdArg = "album_details_Id"
    override val route = "album_details_route/{$albumDetailsIdArg}"
    override val destination = "album_details_destination"


    fun createNavigationRoute(albumDetailsIdArg: String): String {
        val encodedId = Uri.encode(albumDetailsIdArg)
        return "album_details_route/$encodedId"
    }

    fun fromNavArgs(entry: NavBackStackEntry): String {
        val encodedId = entry.arguments?.getString(albumDetailsIdArg)!!
        return Uri.decode(encodedId)
    }
}

fun NavGraphBuilder.albumDetailsGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = AlbumDetailsDestination.route,
        arguments = listOf(
            navArgument(AlbumDetailsDestination.albumDetailsIdArg) { type = NavType.StringType }
        )
    ) {
        albumDetailsRoute(onBackClick = onBackClick,albumId = AlbumDetailsDestination.fromNavArgs(it))
    }
}