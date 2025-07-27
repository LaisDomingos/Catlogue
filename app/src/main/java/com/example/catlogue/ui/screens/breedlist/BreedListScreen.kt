package com.example.catlogue.ui.screens.breedlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import com.example.catlogue.viewmodel.BreedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    breeds: List<Breed>,
    breedViewModel: BreedViewModel,
    modifier: Modifier = Modifier,
    onBreedClick: (Breed) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // Observa os favoritos do ViewModel
    val favoriteBreeds by breedViewModel.favoriteBreeds.collectAsState()

    // Filtra os breeds pelo searchQuery
    val filteredBreeds = breeds.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    // Função pra checar se um breed é favorito
    fun isFavorite(breed: Breed) = favoriteBreeds.any { it.id == breed.id }

    Scaffold(
        // topBar, bottomBar etc podem ser adicionados aqui
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
                        isFavorite = isFavorite(breed),
                        onToggleFavorite = { b ->
                            println("DEBUG: Clique no coração do breed ${b.name} (id: ${b.id}), favorito? ${isFavorite(b)}")
                            if (isFavorite(b)) {
                                breedViewModel.removeFavorite(b)
                            } else {
                                breedViewModel.addFavorite(b)
                            }
                        },
                        onClick = { onBreedClick(breed) }
                    )
                }

            }
        }
    }
}

@Composable
fun BreedGridItem(
    breed: Breed,
    isFavorite: Boolean,
    onToggleFavorite: (Breed) -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
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
                onClick = { onToggleFavorite(breed) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Desfavoritar" else "Favoritar",
                    tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
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
