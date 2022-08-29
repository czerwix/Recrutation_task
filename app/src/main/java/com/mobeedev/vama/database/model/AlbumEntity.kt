package com.mobeedev.vama.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobeedev.vama.album.ui.model.GenreItem

@Entity(
    tableName = "album",
)
data class AlbumEntity(
    @PrimaryKey
    val id: String,

    val artworkUrl100: String? = null,
    val contentAdvisoryRating: String? = null,
    val releaseDate: String? = null,
    val kind: String? = null,
    val artistUrl: String? = null,
    val name: String? = null,
    val artistName: String? = null,
    val artistId: String? = null,
    val url: String? = null
)