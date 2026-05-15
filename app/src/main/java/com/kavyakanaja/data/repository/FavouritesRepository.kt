package com.kavyakanaja.data.repository

import com.kavyakanaja.data.db.FavouriteDao
import com.kavyakanaja.data.db.FavouriteEntity
import kotlinx.coroutines.flow.Flow

class FavouritesRepository(private val dao: FavouriteDao) {

    fun getAllFavouriteIds(): Flow<List<FavouriteEntity>> = dao.getAllFavourites()

    fun isFavourite(poemId: Int): Flow<Boolean> = dao.isFavourite(poemId)

    suspend fun toggleFavourite(poemId: Int) {
        val ids = dao.getAllFavouriteIds()
        if (poemId in ids) {
            dao.removeFavouriteById(poemId)
        } else {
            dao.addFavourite(FavouriteEntity(poemId))
        }
    }

    suspend fun getFavouriteIds(): List<Int> = dao.getAllFavouriteIds()
}
