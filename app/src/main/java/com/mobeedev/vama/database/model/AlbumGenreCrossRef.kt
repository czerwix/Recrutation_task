package com.mobeedev.vama.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "album_genre",
    primaryKeys = ["album_id", "genre_id"],
    foreignKeys = [
        ForeignKey(
            entity = AlbumEntity::class,
            parentColumns = ["id"],
            childColumns = ["album_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genre_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["album_id"]),
        Index(value = ["genre_id"]),
    ],
)
data class AlbumGenreCrossRef(
    @ColumnInfo(name = "album_id")
    val albumId: String,
    @ColumnInfo(name = "genre_id")
    val genreId: String,
)