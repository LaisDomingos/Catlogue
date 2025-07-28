package com.example.catlogue.ui.screens.breedlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.catlogue.data.model.Breed
import com.example.catlogue.viewmodel.BreedViewModel
import com.example.catlogue.ui.components.BreedGridItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    breeds: List<Breed>,
    breedViewModel: BreedViewModel, // seu viewModel já está aqui
    modifier: Modifier = Modifier,
    onBreedClick: (Breed) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val favoriteBreeds by breedViewModel.favoriteBreeds.collectAsState()

    // Filtra os breeds pelo searchQuery
    val filteredBreeds = breeds.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    fun isFavorite(breed: Breed) = favoriteBreeds.any { it.id == breed.id }

    val isLoading by breedViewModel.isLoading.collectAsState()

    Scaffold {
            innerPadding ->
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

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(filteredBreeds.size) { index ->
                    val breed = filteredBreeds[index]
                    BreedGridItem(
                        breed = breed,
                        isFavorite = isFavorite(breed),
                        onToggleFavorite = { b ->
                            if (isFavorite(b)) {
                                breedViewModel.removeFavorite(b)
                            } else {
                                breedViewModel.addFavorite(b)
                            }
                        },
                        onClick = { onBreedClick(breed) }
                    )

                    // Quando chegar no fim, carrega mais
                    if (index >= breedViewModel.breeds.value.size - 1 && !isLoading) {
                        breedViewModel.loadMoreBreeds()
                    }
                }

                // Mostrar progress indicator enquanto carrega
                if (isLoading) {
                    item(span = { GridItemSpan(2) }) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}
