package com.kavyakanaja.data.model

data class WordMeaning(
    val word: String = "",
    val meaning: String = "",
    val kannada: String = ""
)

data class Poem(
    val id: Int = 0,
    val title: String = "",
    val poet: String = "",
    val poetEn: String = "",
    val era: String = "",
    val eraEn: String = "",
    val text: String = "",
    val meaning: String = "",
    val audioFile: String = "",
    val wordMeanings: List<WordMeaning> = emptyList(),
    val tags: List<String> = emptyList()
)
