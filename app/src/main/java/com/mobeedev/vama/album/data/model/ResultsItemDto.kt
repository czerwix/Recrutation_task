package com.mobeedev.vama.album.data.model

import com.mobeedev.vama.album.ui.model.AlbumItem

data class ResultsItemDto(
    val artworkUrl100: String? = null,
    val contentAdvisoryRating: String? = null,
    val releaseDate: String? = null,
    val kind: String? = null,
    val artistUrl: String? = null,
    val genres: List<GenresItemDto>,
    val name: String? = null,
    val artistName: String? = null,
    val artistId: String? = null,
    val id: String,
    val url: String? = null
)

fun ResultsItemDto.asItem() = AlbumItem(
    artworkUrl100 = artworkUrl100,
    contentAdvisoryRating = contentAdvisoryRating,
    releaseDate = releaseDate,
    kind = kind,
    artistUrl = artistUrl,
    genres = genres.map { it.asItem() },
    name = name,
    artistName = artistName,
    artistId = artistId,
    id = id,
    url = url
)