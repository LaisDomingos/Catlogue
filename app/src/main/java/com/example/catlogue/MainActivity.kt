package com.example.catlogue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.catlogue.data.local.DatabaseBuilder
import com.example.catlogue.data.remote.RetrofitInstance
import com.example.catlogue.data.repository.BreedRepository
import com.example.catlogue.ui.screens.breedlist.BreedListScreen
import com.example.catlogue.ui.theme.CatlogueTheme
import com.example.catlogue.viewmodel.BreedViewModel
import com.example.catlogue.viewmodel.BreedViewModelFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.padding

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
                // Só chama sua BreedListScreen direto
                val breeds = viewModel.breeds.collectAsState(initial = emptyList())

                BreedListScreen(
                    breeds = breeds.value
                )
            }
        }
    }
}

