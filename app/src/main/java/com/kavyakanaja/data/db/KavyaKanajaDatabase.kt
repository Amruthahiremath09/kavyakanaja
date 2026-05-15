package com.kavyakanaja.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KavyaKanajaDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        @Volatile private var INSTANCE: KavyaKanajaDatabase? = null

        fun getDatabase(context: Context): KavyaKanajaDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KavyaKanajaDatabase::class.java,
                    "kavya_kanaja_db"
                ).build().also { INSTANCE = it }
            }
    }
}
