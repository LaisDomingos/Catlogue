package com.example.catlogue.ui.screens.breedlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.catlogue.data.model.Breed
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    breeds: List<Breed>,
    modifier: Modifier = Modifier,
    onBreedClick: (Breed) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredBreeds = breeds.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        // topBar, bottomBar etc
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search breed") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, vertical = 1.dp),
                shape = RoundedCornerShape(24.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(filteredBreeds) { breed ->
                    BreedGridItem(
                        breed = breed,
                        onClick = { onBreedClick(breed) } // Passa o clique aqui
                    )
                }
            }
        }
    }
}

@Composable
fun BreedGridItem(
    breed: Breed,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },  // Torna o card clicável
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(breed.image?.url),
                contentDescription = breed.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { /* lógica de favoritar */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favoritar",
                    tint = MaterialTheme.colorScheme.error
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = breed.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}