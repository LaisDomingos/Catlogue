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
    val favoriteBreeds by breedViewModel.favoriteBreeds.collectAsState()
    val errorMessage by breedViewModel.error.collectAsState()

    // Controla se o dialog está visível
    var showDialog by remember { mutableStateOf(false) }

    // Toda vez que errorMessage mudar, decide mostrar dialog
    LaunchedEffect(errorMessage) {
        showDialog = !errorMessage.isNullOrEmpty()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (favoriteBreeds.isEmpty()) {
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
                                breedViewModel.removeFavorite(b)
                            },
                            onClick = { onBreedClick(breed) },
                            showLifeSpan = true
                        )
                    }
                }
            }
        }

        // AlertDialog para mostrar o erro
        if (showDialog && !errorMessage.isNullOrEmpty()) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    breedViewModel.clearError()
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            breedViewModel.clearError()
                        }
                    ) {
                        Text("OK")
                    }
                },
                title = { Text("Erro") },
                text = { Text(errorMessage ?: "Erro desconhecido") }
            )
        }
    }
}
