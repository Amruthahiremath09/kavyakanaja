package com.kavyakanaja.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey val poemId: Int,
    val savedAt: Long = System.currentTimeMillis()
)

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourites ORDER BY savedAt DESC")
    fun getAllFavourites(): Flow<List<FavouriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE poemId = :id)")
    fun isFavourite(id: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(fav: FavouriteEntity)

    @Delete
    suspend fun removeFavourite(fav: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE poemId = :id")
    suspend fun removeFavouriteById(id: Int)

    @Query("SELECT poemId FROM favourites")
    suspend fun getAllFavouriteIds(): List<Int>
}
