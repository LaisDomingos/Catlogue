package com.example.catlogue.ui.screens.breeddetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.catlogue.data.model.Breed
import com.example.catlogue.viewmodel.BreedViewModel

@Composable
fun BreedDetailsScreen(
    breed: Breed,
    viewModel: BreedViewModel,
    modifier: Modifier = Modifier
) {
    val darkBlue = Color(0xFF0D47A1)

    // Estado local de favorito
    var isFavorite by remember { mutableStateOf(false) }

    // Verifica se é favorito ao carregar a tela
    LaunchedEffect(breed.id) {
        viewModel.isFavorite(breed.id) { result ->
            isFavorite = result
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
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
                    onClick = {
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            viewModel.addFavorite(breed)
                        } else {
                            viewModel.removeFavorite(breed)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "Desfavoritar" else "Favoritar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = breed.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue
                )
                Text(
                    text = "Origin: ${breed.origin}",
                    fontSize = 18.sp,
                    color = darkBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Temperament:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = darkBlue
        )
        Text(
            text = breed.temperament,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Details:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = darkBlue
        )
        Text(
            text = breed.description,
            fontSize = 16.sp
        )
    }
}
