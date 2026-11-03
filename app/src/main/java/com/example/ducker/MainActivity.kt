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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.navigation.Destination
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
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
        Button(onClick = { onNavigate(Destination.MyFridge) }) {
            Text(text = "Aller aux Frigo")
        }

        Button(onClick = { onNavigate(Destination.UsersPage) }) {
            Text(text = "Aller au Users")
        }
        Button(
            onClick = { onNavigate(Destination.LikePage)},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            )

        ) {
            Text(text = "Aller au Favoris")
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