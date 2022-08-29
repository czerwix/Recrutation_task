package com.mobeedev.vama.database.dao

import androidx.room.*
import com.mobeedev.vama.database.model.AlbumEntity
import com.mobeedev.vama.database.model.AlbumGenreCrossRef
import com.mobeedev.vama.database.model.PopulatedAlbum

@Dao
interface AlbumDao {
    @Query(value = "SELECT * FROM album")
    fun getAlbums(): List<PopulatedAlbum>

    @Query(value = "SELECT * FROM album WHERE id=:id")
    fun getAlbum(id: String): PopulatedAlbum

    /**
     * Inserts [albumEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAlbum(albumEntities: List<AlbumEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateAlbum(entities: List<AlbumEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertAlbum(entities: List<AlbumEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreAlbum,
        updateMany = ::updateAlbum
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAlbumGenreCrossRefEntities(
        albumGenreCrossRef: List<AlbumGenreCrossRef>
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM album
            WHERE id in (:ids)
        """
    )
    suspend fun deleteAlbums(ids: List<String>)

    @Query(
        value = """
            DELETE FROM album_genre
        """
    )
    suspend fun deleteAlbumsCrossRef()

}