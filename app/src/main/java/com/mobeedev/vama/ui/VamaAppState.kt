/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mobeedev.vama.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.tracing.trace
import com.mobeedev.vama.album.ui.details.navigation.AlbumDetailsDestination
import com.mobeedev.vama.navigation.VamaNavigationDestination

@Composable
fun rememberVamaAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): VamaAppState {
    return remember(navController, windowSizeClass) {
        VamaAppState(navController, windowSizeClass)
    }
}

@Stable
class VamaAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    var topBarState = true

    /**
     * @param destination: The [VamaNavigationDestination] the app needs to navigate to.
     * @param route: Optional route to navigate to in case the destination contains arguments.
     */
    fun navigate(destination: VamaNavigationDestination, route: String? = null) {
        trace("Navigation: $destination") {
            topBarState = when (destination.route) {
                AlbumDetailsDestination.destination -> false
                else -> true
            }
            navController.navigate(route ?: destination.route)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}
