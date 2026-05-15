package com.kavyakanaja.ui.poem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavyakanaja.data.model.Poem
import com.kavyakanaja.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllPoemsScreen(
    poems: List<Poem>,
    onPoemClick: (Poem) -> Unit,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filtered = remember(query, poems) {
        if (query.isBlank()) poems
        else poems.filter {
            it.title.contains(query, true) ||
            it.poet.contains(query, true) ||
            it.poetEn.contains(query, true) ||
            it.tags.any { tag -> tag.contains(query, true) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ಎಲ್ಲ ಕವನ  •  All Poems", fontSize = 17.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor      = SaffronDark,
                    titleContentColor   = Color.White,
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
        ) {
            // Search bar
            OutlinedTextField(
                value         = query,
                onValueChange = { query = it },
                modifier      = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                placeholder   = { Text("ಹುಡುಕಿ  •  Search poems, poets...") },
                leadingIcon   = { Icon(Icons.Default.Search, contentDescription = null) },
                shape         = RoundedCornerShape(12.dp),
                singleLine    = true,
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = SaffronDark,
                    unfocusedBorderColor = InkLight,
                    focusedLabelColor    = SaffronDark
                )
            )

            Text(
                text     = "${filtered.size} ಕವನಗಳು  •  poems",
                style    = MaterialTheme.typography.labelMedium.copy(color = InkLight),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered, key = { it.id }) { poem ->
                    PoemListCard(poem = poem, onClick = { onPoemClick(poem) })
                }
            }
        }
    }
}

@Composable
fun PoemListCard(poem: Poem, onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Left accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(SaffronGold)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text     = poem.title,
                    style    = MaterialTheme.typography.headlineMedium.copy(
                        color = InkBrown, fontSize = 17.sp, fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text  = "✍️ ${poem.poet}  •  ${poem.era}",
                    style = MaterialTheme.typography.labelMedium.copy(color = SaffronDark)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text     = poem.text.lines().first(),
                    style    = MaterialTheme.typography.bodyMedium.copy(
                        color = InkLight, fontSize = 13.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
