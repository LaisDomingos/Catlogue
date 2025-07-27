package com.example.catlogue.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.catlogue.data.model.Breed
import com.example.catlogue.viewmodel.BreedViewModel
import com.example.catlogue.ui.components.BreedGridItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    breedViewModel: BreedViewModel,
    modifier: Modifier = Modifier,
    onBreedClick: (Breed) -> Unit
) {
    // Pega direto a lista de favoritos do ViewModel
    val favoriteBreeds by breedViewModel.favoriteBreeds.collectAsState()

    Scaffold(
        // topBar, bottomBar etc podem ser adicionados aqui se quiser
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (favoriteBreeds.isEmpty()) {
                // Mensagem bonitinha quando não tem favoritos ainda
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "You don't have any favorite cats yet 😿",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoriteBreeds) { breed ->
                        BreedGridItem(
                            breed = breed,
                            isFavorite = true,
                            onToggleFavorite = { b ->
                                // Permite desfavoritar direto aqui
                                breedViewModel.removeFavorite(b)
                            },
                            onClick = { onBreedClick(breed) },
                            showLifeSpan = true
                        )
                    }
                }
            }
        }
    }
}
