package com.mobeedev.vama.album.ui.model

import com.mobeedev.vama.database.model.GenreEntity
import java.io.Serializable

data class GenreItem(
    val genreId: String,
    val name: String? = null,
    val url: String? = null
) : Serializable

fun GenreItem.asEntity() = GenreEntity(
    id = genreId,
    name = name,
    url = url
)
