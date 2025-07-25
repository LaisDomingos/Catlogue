package com.example.catlogue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catlogue.ui.theme.CatlogueTheme
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.catlogue.data.local.DatabaseBuilder
import com.example.catlogue.data.remote.RetrofitInstance
import com.example.catlogue.data.repository.BreedRepository
import com.example.catlogue.viewmodel.BreedViewModel
import com.example.catlogue.viewmodel.BreedViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: BreedViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = DatabaseBuilder.getInstance(applicationContext).breedDao()
        val apiService = RetrofitInstance.api
        val repository = BreedRepository(apiService, dao)
        val viewModelFactory = BreedViewModelFactory(repository)
        viewModel = androidx.lifecycle.ViewModelProvider(this, viewModelFactory).get(BreedViewModel::class.java)

        enableEdgeToEdge()

        setContent {
            CatlogueTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text("Raças de Gatos 🐱") })
                    }
                ) { innerPadding ->
                    BreedListScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun BreedListScreen(modifier: Modifier = Modifier, viewModel: BreedViewModel) {
    // Estado local para guardar a lista de raças
    val breeds by viewModel.breeds.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> {
                // Mostrar um loading simples no centro
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
            }
            error != null -> {
                // Mostrar erro se houver
                Text(
                    text = "Erro: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
                )
            }
            else -> {
                // Lista rolável de raças
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(breeds) { breed ->
                        BreedItem(breedName = breed.name, breedOrigin = breed.origin)
                    }
                }
            }
        }
    }
}

@Composable
fun BreedItem(breedName: String, breedOrigin: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = breedName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Origem: $breedOrigin", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
