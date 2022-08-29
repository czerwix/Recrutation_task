package com.mobeedev.vama.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mobeedev.vama.album.ui.model.AlbumItem

data class PopulatedAlbum(
    @Embedded
    val album: AlbumEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = AlbumGenreCrossRef::class,
            parentColumn = "album_id",
            entityColumn = "genre_id",
        )
    )
    val genre: List<GenreEntity>
)

fun PopulatedAlbum.asItem() = AlbumItem(
    id = album.id,
    artworkUrl100 = album.artworkUrl100,
    contentAdvisoryRating = album.contentAdvisoryRating,
    releaseDate = album.releaseDate,
    kind = album.kind,
    artistUrl = album.artistUrl,
    genres = genre.map { it.asItem() },
    name = album.name,
    artistName = album.artistName,
    artistId = album.artistId,
    url = album.url
)