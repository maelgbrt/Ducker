package com.example.ducker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.navigation.NavHost
import com.example.ducker.ui.theme.DuckerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: DuckerViewModel by viewModels {
        DuckerViewModel.Factory((application as DuckerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuckerTheme {
                NavHost(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}