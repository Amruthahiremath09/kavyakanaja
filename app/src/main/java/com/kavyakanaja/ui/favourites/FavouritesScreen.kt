package com.kavyakanaja.ui.favourites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kavyakanaja.data.model.Poem
import com.kavyakanaja.ui.home.MainViewModel
import com.kavyakanaja.ui.poem.PoemListCard
import com.kavyakanaja.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    viewModel: MainViewModel,
    onPoemClick: (Poem) -> Unit,
    onBack: () -> Unit
) {
    val favouriteIds by viewModel.favouriteIds.collectAsStateWithLifecycle()
    val favourites    = remember(favouriteIds) {
        viewModel.allPoems.filter { it.id in favouriteIds }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ಮೆಚ್ಚಿನ ಕವನ  •  Favourites", fontSize = 17.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CrimsonRed,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Parchment
    ) { padding ->
        if (favourites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = InkLight,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text      = "ಯಾವುದೇ ಮೆಚ್ಚಿನ ಕವನ ಇಲ್ಲ\nNo favourites yet",
                        style     = MaterialTheme.typography.bodyLarge.copy(
                            color = InkLight, fontStyle = FontStyle.Italic
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text  = "ಕವನ ಓದುವಾಗ ❤️ ಒತ್ತಿ ಸೇರಿಸಿ",
                        style = MaterialTheme.typography.labelMedium.copy(color = InkLight),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(padding)
            ) {
                item {
                    Text(
                        text  = "${favourites.size} ಮೆಚ್ಚಿನ ಕವನಗಳು",
                        style = MaterialTheme.typography.labelMedium.copy(color = InkLight),
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                }
                items(favourites, key = { it.id }) { poem ->
                    PoemListCard(
                        poem    = poem,
                        onClick = { onPoemClick(poem) }
                    )
                }
            }
        }
    }
}
