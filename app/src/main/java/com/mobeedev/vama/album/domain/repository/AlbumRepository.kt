package com.mobeedev.vama.album.domain.repository

import com.mobeedev.vama.album.ui.model.AlbumItem

interface AlbumRepository {

    suspend fun syncAlbumInfo(): Result<List<AlbumItem>>

    suspend fun getLocalAlbumInfo(): Result<List<AlbumItem>>

    suspend fun getAlbum(id: String): Result<AlbumItem>
}