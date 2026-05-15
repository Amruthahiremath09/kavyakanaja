package com.kavyakanaja.ui.poem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kavyakanaja.data.model.Poem
import com.kavyakanaja.data.model.WordMeaning
import com.kavyakanaja.ui.home.MainViewModel
import com.kavyakanaja.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoemDetailScreen(
    poem: Poem,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val favouriteIds  by viewModel.favouriteIds.collectAsStateWithLifecycle()
    val isPlaying     by viewModel.isPlaying.collectAsStateWithLifecycle()
    val highlightLine by viewModel.highlightedLine.collectAsStateWithLifecycle()
    val context        = LocalContext.current
    val isFav          = poem.id in favouriteIds

    var selectedWord by remember { mutableStateOf<WordMeaning?>(null) }
    var showSheet    by remember { mutableStateOf(false) }

    if (showSheet && selectedWord != null) {
        BhavartheBottomSheet(
            wordMeaning = selectedWord!!,
            onDismiss   = { showSheet = false; selectedWord = null }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text  = poem.title,
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 17.sp),
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleFavourite(poem.id) }) {
                        Icon(
                            imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favourite",
                            tint = if (isFav) CrimsonRed else InkLight
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SaffronDark,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Parchment
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            // ── Poet & Era info ───────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape  = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SaffronGold.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text  = "✍️ ${poem.poet}",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = SaffronDark, fontWeight = FontWeight.Bold, fontSize = 16.sp
                            )
                        )
                        Text(
                            text  = "${poem.poetEn}  •  ${poem.eraEn}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    // Audio button
                    FilledTonalButton(
                        onClick = {
                            viewModel.playAudio(
                                context, poem.audioFile, poem.id, poem.text.lines().size
                            )
                        },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (isPlaying) CrimsonRed else DeepGreen,
                            contentColor   = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(if (isPlaying) "ನಿಲ್ಲಿಸು" else "ಕೇಳು", fontSize = 13.sp)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Poem Text with line highlighting ──────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(6.dp, RoundedCornerShape(16.dp)),
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text  = poem.title,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = InkBrown, fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.height(4.dp))
                    Divider(
                        color     = SaffronGold.copy(alpha = 0.6f),
                        thickness = 1.5.dp
                    )
                    Spacer(Modifier.height(16.dp))

                    // Lines with highlight
                    poem.text.lines().forEachIndexed { index, line ->
                        val bgColor = if (index == highlightLine && isPlaying)
                            SaffronGold.copy(alpha = 0.3f) else Color.Transparent

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(bgColor, RoundedCornerShape(6.dp))
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                        ) {
                            ClickableWordLine(
                                line         = line,
                                wordMeanings = poem.wordMeanings,
                                onWordTap    = { wm ->
                                    selectedWord = wm
                                    showSheet    = true
                                }
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Bhavartha / Overall Meaning ───────────────────────
            if (poem.meaning.isNotBlank()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DeepGreen.copy(alpha = 0.08f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text  = "📚 ಭಾವಾರ್ಥ  •  Meaning",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = DeepGreen, fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text  = poem.meaning,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 24.sp, color = InkBrown
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // ── Tap hint ──────────────────────────────────────────
            if (poem.wordMeanings.isNotEmpty()) {
                Text(
                    text  = "💡 ಕಷ್ಟ ಪದ ಮೇಲೆ ತಟ್ಟಿ ಅರ್ಥ ನೋಡಿ  •  Tap highlighted words for meanings",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = InkLight, fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun ClickableWordLine(
    line: String,
    wordMeanings: List<WordMeaning>,
    onWordTap: (WordMeaning) -> Unit
) {
    val knownWords = wordMeanings.associateBy { it.word }
    val words = line.split(" ")

    val annotated = buildAnnotatedString {
        words.forEachIndexed { idx, word ->
            val clean = word.trim()
            val match  = knownWords[clean]
            if (match != null) {
                pushStringAnnotation(tag = "WORD", annotation = clean)
                withStyle(
                    SpanStyle(
                        color          = CrimsonRed,
                        fontWeight     = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) { append(clean) }
                pop()
            } else {
                withStyle(
                    SpanStyle(
                        color      = InkBrown,
                        fontFamily = FontFamily.Serif
                    )
                ) { append(clean) }
            }
            if (idx < words.lastIndex) append(" ")
        }
    }

    androidx.compose.foundation.text.ClickableText(
        text  = annotated,
        style = MaterialTheme.typography.bodyLarge.copy(
            lineHeight = 32.sp, fontFamily = FontFamily.Serif
        ),
        onClick = { offset ->
            annotated.getStringAnnotations("WORD", offset, offset)
                .firstOrNull()?.let { span ->
                    knownWords[span.item]?.let { wm -> onWordTap(wm) }
                }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BhavartheBottomSheet(
    wordMeaning: WordMeaning,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest  = onDismiss,
        sheetState        = sheetState,
        containerColor    = CardSurface,
        shape             = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 40.dp, top = 8.dp)
        ) {
            Text(
                text  = "📖 ಪದದ ಅರ್ಥ  •  Word Meaning",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = SaffronDark, fontWeight = FontWeight.Bold
                )
            )
            Spacer(Modifier.height(16.dp))

            // The word
            Text(
                text  = wordMeaning.word,
                style = MaterialTheme.typography.displayMedium.copy(
                    color = CrimsonRed, fontWeight = FontWeight.Bold
                )
            )
            Spacer(Modifier.height(12.dp))
            Divider(color = SaffronGold.copy(alpha = 0.5f))
            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text  = "English",
                        style = MaterialTheme.typography.labelMedium.copy(color = InkLight)
                    )
                    Text(
                        text  = wordMeaning.meaning,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = DeepGreen, fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Column(Modifier.weight(1f)) {
                    Text(
                        text  = "ಕನ್ನಡ",
                        style = MaterialTheme.typography.labelMedium.copy(color = InkLight)
                    )
                    Text(
                        text  = wordMeaning.kannada,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = InkBrown, fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick  = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors   = ButtonDefaults.buttonColors(containerColor = SaffronDark)
            ) {
                Text("ಮುಚ್ಚು  •  Close", color = Color.White)
            }
        }
    }
}
