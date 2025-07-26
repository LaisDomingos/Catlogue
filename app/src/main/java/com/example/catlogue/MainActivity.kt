package com.example.catlogue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.catlogue.data.local.DatabaseBuilder
import com.example.catlogue.data.remote.RetrofitInstance
import com.example.catlogue.data.repository.BreedRepository
import com.example.catlogue.ui.components.BottomNavBar
import com.example.catlogue.ui.components.BottomNavItem
import com.example.catlogue.ui.components.NavBar
import com.example.catlogue.ui.screens.breedlist.BreedListScreen
import com.example.catlogue.ui.theme.CatlogueTheme
import com.example.catlogue.viewmodel.BreedViewModel
import com.example.catlogue.viewmodel.BreedViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: BreedViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = DatabaseBuilder.getInstance(applicationContext).breedDao()
        val apiService = RetrofitInstance.api
        val repository = BreedRepository(apiService, dao)
        val viewModelFactory = BreedViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BreedViewModel::class.java)

        enableEdgeToEdge()

        setContent {
            CatlogueTheme {
                var selectedItem by remember { mutableStateOf(BottomNavItem.Home) }
                val breeds = viewModel.breeds.collectAsState(initial = emptyList())

                Scaffold(
                    topBar = { NavBar() },
                    bottomBar = {
                        BottomNavBar(
                            selectedItem = selectedItem,
                            onItemSelected = { selectedItem = it }
                        )
                    }
                ) { paddingValues ->
                    when (selectedItem) {
                        BottomNavItem.Home -> {
                            BreedListScreen(
                                breeds = breeds.value,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                        BottomNavItem.Favorite -> {
                            Surface(
                                modifier = Modifier.padding(paddingValues),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                Text(
                                    text = "Favoritos (em construção 🛠️)",
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
