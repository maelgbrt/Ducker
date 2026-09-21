package com.example.ducker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ducker.ui.theme.DuckerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuckerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HelloWorld(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable

fun HelloWorld(modifier: Modifier = Modifier) {

    // Déclaration de Variables
    var compteur by remember { mutableStateOf(0) }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Hello World", fontSize = 28.sp)
        Button( onClick = { compteur ++}) {
            Text(text = "Clique Me")
        }
        Text(text = "Voici le compteur : $compteur")
        Button(onClick = {

        }, colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE91E63), // Couleur de fond du bouton (ex: Rose)
            contentColor = Color.White          // Couleur du texte et des icônes à l'intérieur
        )) {
            Text(text = "Lien Page 2", fontSize = 24.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HelloWorldPreview() {
    DuckerTheme {
        HelloWorld()
    }
}