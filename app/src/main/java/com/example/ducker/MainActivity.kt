package com.example.ducker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ducker.ui.theme.DuckerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.theme.DuckerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: DuckerViewModel by viewModels {
        DuckerViewModel.Factory((application as DuckerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            DuckerTheme(darkTheme = viewModel.isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onNavigate: (Destination) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onNavigate(Destination.FridgePage) }) {
            Text(text = "Aller aux Frigo")
        }

        /*Button(onClick = {
            onNavigate(Destination.UsersPage)
        }) {
            Text(text = "Aller au Users")
        }*/
        Button(
            onClick = { onNavigate(Destination.SettingsPage) }
        ) {
            Text(text = "Aller aux paramètres")
        }
        Text(text = "Hello World", fontSize = 28.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PageAcceuilPreview() {
    DuckerTheme {
        HomePage(onNavigate = {})
    }
}