package com.kavyakanaja.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kavyakanaja.data.model.Poem
import java.util.Calendar

class PoemRepository(private val context: Context) {

    private val _poems: List<Poem> by lazy { loadPoems() }

    val poems: List<Poem> get() = _poems

    val poemOfTheDay: Poem?
        get() {
            if (_poems.isEmpty()) return null
            val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
            val index = dayOfYear % _poems.size
            return _poems[index]
        }

    fun getPoemById(id: Int): Poem? = _poems.firstOrNull { it.id == id }

    fun getPoemsByPoet(poetEn: String): List<Poem> =
        _poems.filter { it.poetEn.equals(poetEn, ignoreCase = true) }

    fun getPoemsByTag(tag: String): List<Poem> =
        _poems.filter { tag in it.tags }

    fun getPoemsByIds(ids: List<Int>): List<Poem> =
        _poems.filter { it.id in ids }

    private fun loadPoems(): List<Poem> {
        return try {
            val json = context.assets.open("poems.json")
                .bufferedReader()
                .use { it.readText() }
            val type = object : TypeToken<List<Poem>>() {}.type
            Gson().fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
