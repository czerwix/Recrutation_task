package com.mobeedev.vama.album.data.datasource.local

import com.mobeedev.vama.album.ui.model.AlbumItem
import com.mobeedev.vama.album.ui.model.asEntity
import com.mobeedev.vama.database.dao.AlbumDao
import com.mobeedev.vama.database.dao.GenreDao
import com.mobeedev.vama.database.model.AlbumGenreCrossRef
import com.mobeedev.vama.database.model.asItem

class AlbumLocalDataSource(
    private val albumDao: AlbumDao,
    private val genreDao: GenreDao
) {

    suspend fun getLocalAlbumData() = albumDao.getAlbums().map { it.asItem() }

    suspend fun saveNewAlbumData(newAlbums: List<AlbumItem>) {
        val removeDeRankedAlbums = albumDao.getAlbums()
            .filter { oldAlbum ->
                newAlbums.any { newAlbum -> newAlbum.id == oldAlbum.album.id }
            }
        albumDao.deleteAlbums(removeDeRankedAlbums.map { it.album.id })
        albumDao.deleteAlbumsCrossRef()
        genreDao.deleteGenres()

        genreDao.upsertGenres(entities = newAlbums.flatMap { album -> album.genres.map { it.asEntity() } })
        albumDao.upsertAlbum(newAlbums.map { it.asEntity() })

        albumDao.insertOrIgnoreAlbumGenreCrossRefEntities(
            newAlbums.flatMap { albumItem ->
                albumItem.genres.map { genreItem ->
                    AlbumGenreCrossRef(albumItem.id, genreItem.genreId)
                }
            }
        )
    }

    suspend fun getAlbum(id: String) = albumDao.getAlbum(id).asItem()
}