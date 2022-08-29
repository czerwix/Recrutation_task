package com.mobeedev.vama.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobeedev.vama.database.VamaDB.Companion.DATABASE_VERSION
import com.mobeedev.vama.database.dao.AlbumDao
import com.mobeedev.vama.database.dao.GenreDao
import com.mobeedev.vama.database.model.AlbumEntity
import com.mobeedev.vama.database.model.AlbumGenreCrossRef
import com.mobeedev.vama.database.model.GenreEntity


@Database(
    entities = [
        AlbumEntity::class,
        GenreEntity::class,
        AlbumGenreCrossRef::class
    ],
    version = DATABASE_VERSION
)
abstract class VamaDB() : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun genreDao(): GenreDao

    companion object {
        const val DATABASE_NAME = "vama_db"
        const val DATABASE_VERSION = 1

        @Volatile
        private var INSTANCE: VamaDB? = null

        fun getInstance(context: Context): VamaDB {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    //todo change me. only for development purposes
//                    instance = Room.inMemoryDatabaseBuilder(context, VamaDB::class.java)
//                        .fallbackToDestructiveMigration()
//                        .build()

                    instance = Room.databaseBuilder(context, VamaDB::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}