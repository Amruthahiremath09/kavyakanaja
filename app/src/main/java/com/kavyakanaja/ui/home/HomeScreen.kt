package com.kavyakanaja.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.kavyakanaja.data.model.Poem
import com.kavyakanaja.ui.Routes
import com.kavyakanaja.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavController) {
    val favouriteIds by viewModel.favouriteIds.collectAsStateWithLifecycle()
    val isPlaying    by viewModel.isPlaying.collectAsStateWithLifecycle()
    val context       = LocalContext.current
    val poem          = viewModel.poemOfTheDay

    val today = remember {
        SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(Date())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Parchment)
            .verticalScroll(rememberScrollState())
    ) {
        // ── App Header ────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(SaffronDark, SaffronGold))
                )
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Column {
                Text(
                    text = "📖 ಕಾವ್ಯ ಕಣಜ",
                    style = MaterialTheme.typography.displayMedium.copy(
                        color = Color.White, fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Kavya-Kanaja — Poetry Granary",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.85f)
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = today,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White.copy(alpha = 0.7f)
                    )
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // ── Poem of the Day Card ──────────────────────────────────
        Text(
            text = "ಇಂದಿನ ಕವನ  •  Poem of the Day",
            style = MaterialTheme.typography.labelLarge.copy(
                color = SaffronDark, fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(8.dp))

        if (poem != null) {
            PoemOfTheDayCard(
                poem        = poem,
                isFavourite = poem.id in favouriteIds,
                isPlaying   = isPlaying,
                onFavClick  = { viewModel.toggleFavourite(poem.id) },
                onPlayClick = {
                    val lineCount = poem.text.lines().size
                    viewModel.playAudio(context, poem.audioFile, poem.id, lineCount)
                },
                onReadClick = { navController.navigate("poem_detail/${poem.id}") }
            )
        }

        Spacer(Modifier.height(24.dp))

        // ── Navigation Tiles ──────────────────────────────────────
        Text(
            text = "ಅನ್ವೇಷಿಸಿ  •  Explore",
            style = MaterialTheme.typography.labelLarge.copy(
                color = SaffronDark, fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NavTile(
                icon  = Icons.Default.MenuBook,
                label = "ಎಲ್ಲ ಕವನ\nAll Poems",
                color = DeepGreen,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Routes.ALL_POEMS) }
            )
            NavTile(
                icon  = Icons.Default.Person,
                label = "ಕವಿಗಳು\nPoets",
                color = SaffronDark,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Routes.POETS) }
            )
            NavTile(
                icon  = Icons.Default.Favorite,
                label = "ಮೆಚ್ಚಿನವು\nFavourites",
                color = CrimsonRed,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Routes.FAVOURITES) }
            )
        }

        Spacer(Modifier.height(24.dp))

        // ── Jnanpith Badge ────────────────────────────────────────
        JnanpithBadge()

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun PoemOfTheDayCard(
    poem: Poem,
    isFavourite: Boolean,
    isPlaying: Boolean,
    onFavClick: () -> Unit,
    onPlayClick: () -> Unit,
    onReadClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape   = RoundedCornerShape(16.dp),
        colors  = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // Poet + era row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text  = poem.poet,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = SaffronDark, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text  = poem.era,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                IconButton(onClick = onFavClick) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite",
                        tint = if (isFavourite) CrimsonRed else InkLight
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Divider(color = SaffronGold.copy(alpha = 0.4f))
            Spacer(Modifier.height(14.dp))

            // Title
            Text(
                text  = poem.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = InkBrown, fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(12.dp))

            // Poem text — first 2 lines as preview
            val preview = poem.text.lines().take(2).joinToString("\n")
            Text(
                text  = preview,
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 32.sp, color = InkBrown
                )
            )
            if (poem.text.lines().size > 2) {
                Text(
                    text  = "...",
                    style = MaterialTheme.typography.bodyLarge.copy(color = InkLight)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onPlayClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DeepGreen)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(if (isPlaying) "ನಿಲ್ಲಿಸು" else "▶ ಕೇಳು", fontSize = 13.sp)
                }
                Button(
                    onClick = onReadClick,
                    modifier = Modifier.weight(1f),
                    colors  = ButtonDefaults.buttonColors(containerColor = SaffronDark)
                ) {
                    Text("ಓದು  •  Read", fontSize = 13.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun NavTile(
    icon: ImageVector,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        shape  = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text      = label,
                style     = MaterialTheme.typography.labelMedium.copy(color = Color.White),
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun JnanpithBadge() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape  = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ParchmentDark),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text  = "🏆 ಕರ್ನಾಟಕದ ಹೆಮ್ಮೆ",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = SaffronDark
                ),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text  = "Karnataka has 8 Jnanpith Award Winners — more than any other state in India.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = InkLight, fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
