package com.mobeedev.vama.database.dao

import androidx.room.*
import com.mobeedev.vama.database.model.GenreEntity

@Dao
interface GenreDao {
    @Query(
        value = """
        SELECT * FROM genre
        WHERE id = :genreId
    """
    )
    fun getGenreEntity(genreId: String): GenreEntity

    @Query(value = "SELECT * FROM genre")
    fun getGenreEntities(): List<GenreEntity>

    /**
     * Inserts [GenreEntity] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreGenres(genreEntities: List<GenreEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateGenres(entities: List<GenreEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertGenres(entities: List<GenreEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreGenres,
        updateMany = ::updateGenres
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM genre
            WHERE id in (:ids)
        """
    )
    suspend fun deleteGenres(ids: List<String>): Int
}