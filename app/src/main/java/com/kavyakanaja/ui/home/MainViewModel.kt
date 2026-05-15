package com.kavyakanaja.ui.home

import android.app.Application
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kavyakanaja.KavyaKanajaApp
import com.kavyakanaja.data.model.Poem
import com.kavyakanaja.data.model.Poet
import com.kavyakanaja.data.model.PoetData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as KavyaKanajaApp
    private val poemRepo = app.poemRepository
    private val favRepo  = app.favouritesRepository

    // ── All poems ────────────────────────────────────────────────
    val allPoems: List<Poem> get() = poemRepo.poems

    // ── Poem of the day ──────────────────────────────────────────
    val poemOfTheDay: Poem? get() = poemRepo.poemOfTheDay

    // ── Favourite IDs (Flow) ─────────────────────────────────────
    val favouriteIds: StateFlow<Set<Int>> = favRepo.getAllFavouriteIds()
        .map { list -> list.map { it.poemId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    // ── Favourite poems ──────────────────────────────────────────
    val favouritePoems: StateFlow<List<Poem>>
        get() = favouriteIds.map { ids ->
            poemRepo.getPoemsByIds(ids.toList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // ── Poets ────────────────────────────────────────────────────
    val poets: List<Poet> = PoetData.poets

    // ── Selected poem ─────────────────────────────────────────────
    private val _selectedPoem = MutableStateFlow<Poem?>(null)
    val selectedPoem: StateFlow<Poem?> = _selectedPoem.asStateFlow()

    fun selectPoem(poem: Poem) { _selectedPoem.value = poem }

    // ── Toggle favourite ─────────────────────────────────────────
    fun toggleFavourite(poemId: Int) {
        viewModelScope.launch { favRepo.toggleFavourite(poemId) }
    }

    // ── Audio playback ───────────────────────────────────────────
    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _highlightedLine = MutableStateFlow(0)
    val highlightedLine: StateFlow<Int> = _highlightedLine.asStateFlow()

    private val _currentPoemAudio = MutableStateFlow<Int?>(null)

    fun playAudio(context: android.content.Context, audioFile: String, poemId: Int, lineCount: Int) {
        if (_currentPoemAudio.value == poemId && mediaPlayer?.isPlaying == true) {
            pauseAudio()
            return
        }
        stopAudio()
        _currentPoemAudio.value = poemId
        _highlightedLine.value = 0

        // Use a silent placeholder audio (since we have no actual MP3 files)
        // In a real app: val resId = context.resources.getIdentifier(audioFile, "raw", context.packageName)
        // We simulate playback with timed line highlighting
        _isPlaying.value = true
        scheduleLineHighlight(lineCount)
    }

    private fun scheduleLineHighlight(lineCount: Int) {
        val delayMs = 2500L
        var line = 0
        val runnable = object : Runnable {
            override fun run() {
                if (!_isPlaying.value) return
                _highlightedLine.value = line
                line++
                if (line < lineCount) {
                    handler.postDelayed(this, delayMs)
                } else {
                    _isPlaying.value = false
                    _highlightedLine.value = -1
                }
            }
        }
        handler.postDelayed(runnable, delayMs)
    }

    fun pauseAudio() {
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.pause()
        _isPlaying.value = false
    }

    fun stopAudio() {
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _isPlaying.value = false
        _highlightedLine.value = -1
        _currentPoemAudio.value = null
    }

    override fun onCleared() {
        super.onCleared()
        stopAudio()
    }
}
