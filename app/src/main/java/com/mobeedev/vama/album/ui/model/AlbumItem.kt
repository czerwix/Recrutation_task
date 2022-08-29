package com.mobeedev.vama.album.ui.model

import com.mobeedev.vama.database.model.AlbumEntity
import java.io.Serializable

data class AlbumItem(
    val artworkUrl100: String? = null,
    val contentAdvisoryRating: String? = null,
    val releaseDate: String? = null,
    val kind: String? = null,
    val artistUrl: String? = null,
    val genres: List<GenreItem> = mutableListOf(),
    val name: String? = null,
    val artistName: String? = null,
    val artistId: String? = null,
    val id: String,
    val url: String? = null
):Serializable

fun AlbumItem.asEntity() = AlbumEntity(
    id = id,
    artworkUrl100 = artworkUrl100,
    contentAdvisoryRating = contentAdvisoryRating,
    releaseDate = releaseDate,
    kind = kind,
    artistUrl = artistUrl,
    name = name,
    artistName = artistName,
    artistId = artistId,
    url = url
)
