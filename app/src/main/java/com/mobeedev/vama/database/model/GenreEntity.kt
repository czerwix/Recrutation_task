package com.mobeedev.vama.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobeedev.vama.album.ui.model.GenreItem

@Entity(
    tableName = "genre"
)
data class GenreEntity(
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val url: String? = null
)

fun GenreEntity.asItem() = GenreItem(
    genreId = id,
    name = name,
    url = url
)