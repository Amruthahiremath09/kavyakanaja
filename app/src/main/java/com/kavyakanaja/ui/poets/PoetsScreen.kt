package com.kavyakanaja.ui.poets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavyakanaja.data.model.Poem
import com.kavyakanaja.data.model.Poet
import com.kavyakanaja.ui.poem.PoemListCard
import com.kavyakanaja.ui.theme.*

// ── Poets List ────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoetsScreen(
    poets: List<Poet>,
    onPoetClick: (Poet) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ಕವಿಗಳ ಮೂಲೆ  •  Poets' Corner", fontSize = 17.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SaffronDark,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Parchment
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(padding)
        ) {
            item {
                Text(
                    text = "ಕರ್ನಾಟಕದ ಜ್ಞಾನಪೀಠ ಪ್ರಶಸ್ತಿ ವಿಜೇತರು",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = SaffronDark, fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )
            }
            items(poets, key = { it.id }) { poet ->
                PoetCard(poet = poet, onClick = { onPoetClick(poet) })
            }
        }
    }
}

@Composable
fun PoetCard(poet: Poet, onClick: () -> Unit) {
    Card(
        modifier  = Modifier.fillMaxWidth().clickable { onClick() },
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar circle with initials
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(SaffronDark),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = poet.nameEn.first().toString(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp
                    )
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text  = poet.nameKn,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = InkBrown, fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text  = poet.nameEn,
                    style = MaterialTheme.typography.labelLarge.copy(color = InkLight)
                )
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val lifespan = if (poet.died.isNotEmpty()) "${poet.born}–${poet.died}"
                                   else "b. ${poet.born}"
                    Chip(label = lifespan, color = ParchmentDark)
                    if (poet.jnanpithYear.isNotEmpty()) {
                        Chip(label = "🏆 ${poet.jnanpithYear}", color = SaffronGold.copy(alpha = 0.3f))
                    }
                }
            }
        }
    }
}

// ── Poet Detail ────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoetDetailScreen(
    poet: Poet,
    poems: List<Poem>,
    onPoemClick: (Poem) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(poet.nameKn, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SaffronDark,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
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
            // Hero card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(
                                    androidx.compose.ui.graphics.Brush.radialGradient(
                                        listOf(SaffronGold, SaffronDark)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text  = poet.nameEn.first().toString(),
                                style = MaterialTheme.typography.displayLarge.copy(
                                    color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 32.sp
                                )
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                text  = poet.nameKn,
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = InkBrown, fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text  = poet.nameEn,
                                style = MaterialTheme.typography.labelLarge.copy(color = InkLight)
                            )
                            val life = if (poet.died.isNotEmpty()) "${poet.born} – ${poet.died}"
                                       else "b. ${poet.born}"
                            Text(
                                text  = life,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }

                    if (poet.jnanpithYear.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SaffronGold.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🏆", fontSize = 22.sp)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    text  = "Jnanpith Award ${poet.jnanpithYear}",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = SaffronDark, fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text  = "ಜ್ಞಾನಪೀಠ ಪ್ರಶಸ್ತಿ",
                                    style = MaterialTheme.typography.labelMedium.copy(color = InkLight)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    Divider(color = SaffronGold.copy(alpha = 0.4f))
                    Spacer(Modifier.height(12.dp))

                    Text(
                        text  = "📖 ಜೀವನ ಚರಿತ್ರೆ  •  Biography",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = SaffronDark, fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text  = poet.bioKn,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 28.sp, color = InkBrown
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text  = poet.bioEn,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp, color = InkLight, fontStyle = FontStyle.Italic
                        )
                    )

                    Spacer(Modifier.height(14.dp))
                    Text(
                        text  = "📚 ಪ್ರಮುಖ ಕೃತಿಗಳು  •  Notable Works",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = SaffronDark, fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.height(6.dp))
                    poet.notableWorks.forEach { work ->
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text  = "• $work",
                                style = MaterialTheme.typography.bodyMedium.copy(color = InkBrown)
                            )
                        }
                    }
                }
            }

            // Poems by this poet
            if (poems.isNotEmpty()) {
                Text(
                    text  = "ಈ ಕವಿಯ ಕವನಗಳು  •  Poems by ${poet.nameEn}",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = InkBrown, fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    poems.forEach { poem ->
                        PoemListCard(poem = poem, onClick = { onPoemClick(poem) })
                    }
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun Chip(label: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = InkBrown, fontSize = 11.sp
            )
        )
    }
}
