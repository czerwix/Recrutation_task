package com.mobeedev.vama.album.data.repository

import com.mobeedev.vama.album.data.common.bodyOrException
import com.mobeedev.vama.album.data.datasource.local.AlbumLocalDataSource
import com.mobeedev.vama.album.data.datasource.remote.AlbumService
import com.mobeedev.vama.album.data.model.asItem
import com.mobeedev.vama.album.domain.error.DataErrors
import com.mobeedev.vama.album.domain.error.runRecoverCatching
import com.mobeedev.vama.album.domain.repository.AlbumRepository
import com.mobeedev.vama.album.ui.model.AlbumItem

class AlbumRepositoryImpl(
    private val localAlbumDataSource: AlbumLocalDataSource,
    private val remoteAlbumDataSource: AlbumService
) : AlbumRepository {

    override suspend fun syncAlbumInfo(): Result<List<AlbumItem>> = runRecoverCatching {
        remoteAlbumDataSource.getMostPlayedAlbum().bodyOrException()
    }.map { data ->
        val results = data.feed?.results ?: throw DataErrors.NoData()
        results.mapNotNull { it?.asItem() }
    }.onSuccess {
        localAlbumDataSource.saveNewAlbumData(it)
    }

    override suspend fun getLocalAlbumInfo(): Result<List<AlbumItem>> = runRecoverCatching {
        localAlbumDataSource.getLocalAlbumData()
    }

    override suspend fun getAlbum(id: String): Result<AlbumItem> = runRecoverCatching {
        localAlbumDataSource.getAlbum(id)
    }
}