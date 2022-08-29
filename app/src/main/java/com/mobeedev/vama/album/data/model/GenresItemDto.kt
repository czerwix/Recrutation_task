package com.mobeedev.vama.album.data.model

import com.mobeedev.vama.album.ui.model.GenreItem

data class GenresItemDto(
    val genreId: String,
    val name: String? = null,
    val url: String? = null
)

fun GenresItemDto.asItem() = GenreItem(
    genreId = genreId,
    name = name,
    url = url
)