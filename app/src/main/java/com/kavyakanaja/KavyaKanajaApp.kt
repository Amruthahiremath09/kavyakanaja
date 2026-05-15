package com.kavyakanaja

import android.app.Application
import com.kavyakanaja.data.db.KavyaKanajaDatabase
import com.kavyakanaja.data.repository.FavouritesRepository
import com.kavyakanaja.data.repository.PoemRepository

class KavyaKanajaApp : Application() {
    val poemRepository by lazy { PoemRepository(this) }
    val database by lazy { KavyaKanajaDatabase.getDatabase(this) }
    val favouritesRepository by lazy { FavouritesRepository(database.favouriteDao()) }
}
